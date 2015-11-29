package com.libmanagement.shiro;


import com.libmanagement.entity.Permission;
import com.libmanagement.entity.User;
import com.libmanagement.entity.UserRole;
import com.libmanagement.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class LMSServerRealm extends AuthorizingRealm {
    private static Log logger = LogFactory.getLog(LMSServerRealm.class);


    @Autowired
    private UserService userService;


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userId = (String) principalCollection.getPrimaryPrincipal();


        List<UserRole> userRoleList = userService.findUserRole(userId);


        //角色名的集合
        List<String> roles = new ArrayList<>();
        //权限名的集合
        List<String> permissions = new ArrayList<>();

        for(UserRole userRole:userRoleList){
            roles.add(userRole.getRole().getRoleName());
            for(Permission permission:userRole.getRole().getPermissionList()){
                permissions.add(permission.getPermissionName());
            }
        }


        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);


        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.print("doGetAuthenticationInfo from simple sop");
        logger.debug("doGetAuthenticationInfo...");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUserName(token.getUsername());

        org.apache.shiro.authc.credential.HashedCredentialsMatcher hashedCredentialsMatcher = null;

        if(user!=null){

            if(user.getStatus().equals(User.UserStatus.INVALID.getValue())){
                throw new LockedAccountException();
            }else if(user.getStatus().equals(User.UserStatus.CHECKING.getValue())){
                throw new DisabledAccountException();

            }else {
                return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), user.getUserName());
            }


        }else {
            throw new UnknownAccountException();
        }

    }
}
