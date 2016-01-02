package com.libmanagement.service;


import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.common.utils.DateUtils;
import com.libmanagement.common.utils.MD5;
import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.entity.*;
import com.libmanagement.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginLogRepository loginLogRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private AccountLogRepository accountLogRepository;

    @Autowired
    private ConfigParamService configParamService;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private static final Log logger = LogFactory.getLog(UserService.class);


    /**
     * 创建用户
     *
     * @param userName
     * @param password
     * @return
     */
    public User createUser(String userName, String password, String recommend) throws LMSServerException{

        if(!StringUtils.INSTANCE.isEmail(userName)){
            throw new LMSServerException("请输入正确的邮箱地址");
        }

        if(password == null || password.trim().length()<6){
            throw new LMSServerException("密码不能为空,且最小6位");
        }

        User user = new User();
        user.setStatus(User.UserStatus.CHECKING.getValue());
        user.setUserName(userName);
        user.setEmail(userName);
        user.setPassword(MD5.getMD5(password));
        user.setCreateTime(DateUtils.getNowDateTime());
        user.setRecommend(recommend);

//        String defaultMoneySet = configParamService.findParamValue(User.REGIST_DEFAULT_MONEY);
//        if (!StringUtils.isEmpty(defaultMoneySet) && defaultMoneySet.matches("\\d*")) {
//            user.setAccount(Double.valueOf(defaultMoneySet));
//        }
        user = userRepository.save(user);

        try {
            mailService.sendCheckUserMail(user.getUserName(), getCheckCode(user.getUserName(), user.getCreateTime()));
        } catch (LMSServerException e) {
            throw e;
        }catch (Exception e){
            logger.debug(e,e);
            throw new LMSServerException("异常错误："+e.getMessage());
        }

        return user;
    }

    public void createRegistration(String email, String contactPhone, String name, String title, int sex, String company, String comments) {
        if(!StringUtils.INSTANCE.isEmail(email)){
            throw new LMSServerException("请输入正确的邮箱地址");
        }

        if(registrationRepository.findByEmail(email)!=null) {
            throw new LMSServerException("邮箱地址已注册，如您还未收到我们的反馈，请耐心等候");
        }

        Registration reg = new Registration();

        reg.setEmail(email);
        reg.setContactPhone(contactPhone);
        reg.setName(name);
        reg.setTitle(title);
        reg.setSex(sex);
        reg.setComments(comments);
        reg.setCompany(company);

        registrationRepository.save(reg);
    }

    public User resetPwd(String userName, String password, String code) throws LMSServerException {
        User user = userRepository.findOne(userName);

        if (user == null) {
            throw new LMSServerException("用户不存在");
        }

        if (user.getStatus().equals(User.UserStatus.INVALID.getValue())) {
            throw new LMSServerException("用户被锁定");
        }

        if (code != null && code.equals(getCheckCode(user.getUserName(), user.getUpdateTime()))) {
            user.setPassword(MD5.getMD5(password));
            return userRepository.save(user);

        } else {
            throw new LMSServerException("验证码不正确");
        }

    }

    public User forgotPassword(String userName) throws LMSServerException {
        User user = userRepository.findOne(userName);

        if (user == null) {
            throw new LMSServerException("用户不存在");
        }

        if (user.getStatus().equals(User.UserStatus.INVALID.getValue())) {
            throw new LMSServerException("用户被锁定");
        }

        try {
            mailService.sendForgotPwdMail(user.getUserName(), getCheckCode(user.getUserName(), user.getUpdateTime()));
        } catch (LMSServerException e) {
            throw e;
        }

        return user;
    }

    public boolean checkUser(String userName, String code) throws LMSServerException {
        User user = userRepository.findOne(userName);

        if (user == null) {
            throw new LMSServerException("激活的用户不存在");
        }

        if (!user.getStatus().equals(User.UserStatus.CHECKING.getValue())) {
            throw new LMSServerException("用户已经被激活");
        }

        String rootPath = configParamService.findParamValue(ConfigParamService.HDFS_FILE_ROOTPATH);
        if (code != null && code.equals(getCheckCode(user.getUserName(), user.getCreateTime()))) {
            user.setStatus(User.UserStatus.VALID.getValue());
            userRepository.save(user);

            try {
                if (!StringUtils.INSTANCE.isEmpty(user.getRecommend())) {
                    updateCharge(100.0, user.getRecommend(), "推荐奖励"); //推荐奖励100.0元
                }
            } catch (Exception e) {

            }

            return true;
        }
        return false;
    }

    private String getCheckCode(String userName, long regTime) {
        return MD5.getMD5(userName + regTime);
    }

    /**
     * 判断用户是否存在
     *
     * @param userName
     * @return
     */
    public boolean userExists(String userName) {
        return userRepository.exists(userName);
    }

    public User findByUserName(String userName) throws LMSServerException{
        return userRepository.findByUserName(userName);
    }

    /**
     * 登陆
     *
     * @param userName
     * @param password
     * @return
     */
    public User logon(String userName, String password, String from, String ip) throws LMSServerException {
        User user = userRepository.findByUserName(userName);

        LoginLog log = new LoginLog();
        log.setUser(user);
        log.setFromSource(from);
        log.setLoginIp(ip);
        log.setCreateTime(DateUtils.getNowDateTime());
        if (user == null) {
            log.setLoginStatus(LoginLog.STATUS_USERNOTFOUND);
            log.setLogMessage("用户不存在");
            loginLogRepository.save(log);
            throw new LMSServerException("用户不存在");
        } else if (user.getStatus().equals(User.UserStatus.INVALID.getValue())) {
            log.setLoginStatus(LoginLog.STATUS_USERSTATUSERROR);
            log.setLogMessage("用户被锁定");
            loginLogRepository.save(log);
            throw new LMSServerException("用户被锁定");
        } else if (user.getStatus().equals(User.UserStatus.CHECKING.getValue())) {
            log.setLoginStatus(LoginLog.STATUS_USERSTATUSERROR);
            log.setLogMessage("用户未激活");
            loginLogRepository.save(log);
            throw new LMSServerException("用户还没有激活，请到注册邮箱进行激活操作");
        }

        if (MD5.getMD5(password).equalsIgnoreCase(user.getPassword())) {

            log.setLoginStatus(LoginLog.STATUS_SUCCESS);
            log.setLogMessage("用户登录成功");
            loginLogRepository.save(log);
            user.setUpdateTime(DateUtils.getNowDateTime());
            userRepository.save(user);
            return user;
        } else {
            log.setLoginStatus(LoginLog.STATUS_PASSWORDERROR);
            log.setLogMessage("用户或者密码错误");
            loginLogRepository.save(log);
            throw new LMSServerException("用户或者密码错误");
        }
    }



    /**
     * @param page
     * @return
     */
    public Page<User> listUsers(Pageable page) {

        return userRepository.listUsers(page);

    }

    public List<User> listUsers() {

        return userRepository.findAll();

    }

    public Page<LoginLog> listLoginLogs(Pageable page) {

        return loginLogRepository.list(page);

    }

    public Page<AccountLog> payList(Pageable page) {

        return accountLogRepository.list(page);

    }

    @Transactional
    public void modifyPassword(String oldPassword, String newPassword, User user) throws LMSServerException {
        if (user == null || StringUtils.INSTANCE.isEmpty(user.getUserName())) {
            throw new LMSServerException("用户不存在！");
        } else if (StringUtils.INSTANCE.isAnyEmpty(oldPassword, newPassword)) {
            throw new LMSServerException("密码不能为空！");
        } else {
            User u = userRepository.findOne(user.getUserName());
            if (MD5.getMD5(oldPassword).equals(u.getPassword())) {
                System.out.println("new Password" + newPassword);
                u.setPassword(MD5.getMD5(newPassword));
                userRepository.save(u);
            } else {
                throw new LMSServerException("原密码不正确！");
            }
        }
    }

    public void modifyInfo(User user) throws LMSServerException {
        if (user == null || StringUtils.INSTANCE.isEmpty(user.getUserName())) {
            throw new LMSServerException("用户不存在！");
        } else {
            User oldUser = userRepository.findOne(user.getUserName());
            oldUser.setNickName(user.getNickName());
            oldUser.setRealName(user.getRealName());
            oldUser.setSex(user.getSex());
            oldUser.setEmail(user.getEmail());
            oldUser.setMobile(user.getMobile());

            userRepository.save(oldUser);
        }
    }

    public User findUser(String userId) throws LMSServerException {
        return userRepository.findOne(userId);
    }

    public List<UserRole> findUserRole(String userId) throws LMSServerException {
        return userRoleRepository.findByUserId(userId);
    }

    /**
     * 查询指定时间范围内的帐号流水，beginTime,endTime为null的时候
     * @param beginTime 为null时，默认为七天前时间
     * @param endTime 为null时，默认为当前时间
     * @param type 0 或者null 为全部 ，1 为消费 ，2 为充值
     * @param userName
     * @return
     */
    public List<AccountLog> accountDetail(Long beginTime, Long endTime, Integer type, String userName) {

        if (StringUtils.INSTANCE.isEmpty(beginTime)) {
            beginTime = System.currentTimeMillis() + (-7 * 24 * 60 * 60 * 1000);
        }
        if (StringUtils.INSTANCE.isEmpty(endTime)) {
            endTime = DateUtils.getNowDateTime();
        }
        if (StringUtils.INSTANCE.isEmpty(type) || type==0) {
              return accountLogRepository.findAccountDetail(beginTime, endTime, userName);
        }else {
            return accountLogRepository.findAccountDetail(beginTime, endTime, type, userName);
        }
    }

    /**
     * 用户消费或充值
     *
     * @param balance
     * @param userName
     * @param remark
     * @throws LMSServerException
     */
    public void updateCharge(Double balance, String userName, String remark) throws LMSServerException {
        long score = 0;
        if (balance < 0) {
            score = -balance.longValue();
        }

        updateCharge(balance, score, userName, null, null, remark);
    }

    /**
     * @param balance   金额
     * @param from      来源,admin/portal
     * @param adminUser 当来源为admin时需要填操作原
     * @param remark    备注
     * @throws LMSServerException
     */
    public void updateCharge(Double balance, Long score, String userName, String from, String adminUser, String remark) throws LMSServerException {

        if (StringUtils.INSTANCE.isEmpty(userName)) {
            throw new LMSServerException("用户不存在！");
        } else if (balance == 0) {
            throw new LMSServerException("金额输入不正确！");
        } else {
            User user = userRepository.findOne(userName);

            if(user==null){
                throw new LMSServerException("用户不存在！");
            }

            BigDecimal amount = new BigDecimal(balance);
            BigDecimal oldBalance = new BigDecimal(user.getAccount());
            BigDecimal newBalance = amount.add(oldBalance);

            //保存余额
            user.setAccount(newBalance.doubleValue());


            //记录账户日志
            AccountLog log = new AccountLog();
            if (balance < 0) {
                // 消费
                //获得积分
//				user.setScore(user.getScore()-balance);
                log.setLogType(AccountLog.TYPE_CONSUME);
//				log.setScore(score);
            } else {
                // 充值
                log.setLogType(AccountLog.TYPE_RECHARGE);
//				log.setScore(0);
            }
            user.setScore(user.getScore() + score);
            log.setScore(score);

            log.setAmount(amount.doubleValue());
            log.setBalance(newBalance.doubleValue());
            log.setUser(user);
            log.setCreateTime(DateUtils.getNowDateTime());
            log.setUpdateTime(DateUtils.getNowDateTime());

            if (StringUtils.INSTANCE.isEmpty(from) || from.equals(AccountLog.FROM_PORTAL)) {
                log.setFromType(AccountLog.FROM_PORTAL);
            } else {
                log.setFromType(AccountLog.FROM_ADMIN);
                User admin = userRepository.findOne(adminUser);

            }
            log.setRemark(remark);
             userRepository.save(user);
            accountLogRepository.save(log);
        }
    }


}
