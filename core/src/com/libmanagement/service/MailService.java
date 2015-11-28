package com.libmanagement.service;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.common.utils.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author FlareMars
 */
@Service
public class MailService {

    @Autowired
    private ConfigParamService configParamService;


    public void sendCheckUserMail(String to, String code) throws LMSServerException {

        String host = configParamService.findParamValue(ConfigParamService.MAIL_SMTP_HOST);
        String port = configParamService.findParamValue(ConfigParamService.MAIL_SMTP_PORT);
        String from = configParamService.findParamValue(ConfigParamService.MAIL_FROM);
        String user = configParamService.findParamValue(ConfigParamService.MAIL_USER);
        String password = configParamService.findParamValue(ConfigParamService.MAIL_PASSWORD);

        String subject = "古奥基因(GA)用户注册验证";

        StringBuffer theMessage = new StringBuffer();
        theMessage.append("<br><br>");
        theMessage.append("尊敬的用户，您好！<br><br>");
        theMessage.append("您正在古奥基因(GA)进行用户注册操作，如果是您本人进行的操作，请点击下面链接进行激活。（客服人员不会向您索取此校验码，请勿泄漏！）<br>");
        theMessage.append("------------------------------------------------------------------------------------------------------------------<br><br>");
        theMessage.append("      <a href='http://www.geneapps.cn/user/check?userName=" + to + "&code=" + code + "'>用户激活</a><br>");
        theMessage.append("如果上面链接不能访问，请复制该地址到浏览器地址栏进行激活，http://www.geneapps.cn/user/check?userName=" + to + "&code=" + code + "<br>");
        theMessage.append("<br><br>");
        theMessage.append("此邮件由系统发出，请勿直接回复！<br>");
        theMessage.append("------------------------------------------------------------------------------------------------------------------<br>");
        theMessage.append("古奥基因(GA) GeneApps.cn<br>");
//		theMessage.append("电话：027-88109742<br>");
		theMessage.append("E-mail：service@geneapps.cn<br>");
//		theMessage.append("地址：武汉市武昌区徐东二路2号创意园1栋101室<br>");
		theMessage.append("<br>");

        try {
            Mail.sendMessage(host, port, from, user, password, to, subject, theMessage.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new LMSServerException("验证邮件发送失败");
        }

    }

    public void sendForgotPwdMail(String to, String code) throws LMSServerException {

        String host = configParamService.findParamValue(ConfigParamService.MAIL_SMTP_HOST);
        String port = configParamService.findParamValue(ConfigParamService.MAIL_SMTP_PORT);
        String from = configParamService.findParamValue(ConfigParamService.MAIL_FROM);
        String user = configParamService.findParamValue(ConfigParamService.MAIL_USER);
        String password = configParamService.findParamValue(ConfigParamService.MAIL_PASSWORD);

        String subject = "古奥基因(GA)用户找回密码";

        StringBuffer theMessage = new StringBuffer();
        theMessage.append("<br><br>");
        theMessage.append("尊敬的用户，您好！<br><br>");
        theMessage.append("您正在古奥基因(GA)进行找回密码操作，如果是您本人进行的操作，请点击下面链接进行激活。（客服人员不会向您索取此校验码，请勿泄漏！）<br>");
        theMessage.append("------------------------------------------------------------------------------------------------------------------<br><br>");
        theMessage.append("      <a href='http://www.geneapps.cn/user/resetpwd?userName=" + to + "&code=" + code + "'>重置密码</a><br>");
        theMessage.append("如果上面链接不能访问，请复制该地址到浏览器地址栏进行激活，http://www.geneapps.cn/user/resetpwd?userName=" + to + "&code=" + code + "<br>");
        theMessage.append("<br><br>");
        theMessage.append("此邮件由系统发出，请勿直接回复！<br>");
        theMessage.append("------------------------------------------------------------------------------------------------------------------<br>");
        theMessage.append("古奥基因(GA) GeneApps.cn<br>");
//		theMessage.append("电话：027-88109742<br>");
        theMessage.append("E-mail：service@geneapps.cn<br>");
//		theMessage.append("地址：武汉市武昌区徐东二路2号创意园1栋101室<br>");
        theMessage.append("<br>");

        try {
            Mail.sendMessage(host, port, from, user, password, to, subject, theMessage.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new LMSServerException("验证邮件发送失败");
        }

    }

}
