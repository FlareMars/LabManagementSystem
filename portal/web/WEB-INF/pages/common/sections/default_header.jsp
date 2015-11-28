<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/dms-tags" prefix="dms"%>
<header id="header" class="narrow" data-plugin-options='{"alwaysStickyEnabled": true, "stickyEnabled": true, "stickyWithGap": false, "stickyChangeLogoSize": false}'>
    <div class="container">
        <div class="logo">
            <a href="javascript:void(0)">
                <img alt="Porto" width="111" height="54" data-sticky-width="82" data-sticky-height="40" src="<dms:path/>/assets/default/images/portal_logo.png">
            </a>
        </div>
    </div>
    <div class="navbar-collapse nav-main-collapse collapse">
        <div class="container">
            <nav class="nav-main mega-menu">
                <ul class="nav nav-pills nav-main" id="mainMenu">
                    <li class="<c:if test="${menuItem==null || menuItem == 'index'}">active</c:if>">
                        <a href="javascript:void(0)">
                            首页
                        </a>
                    </li>
                    <%--<li>
                        <a href="javascript:void(0)">服务介绍</a>
                    </li>
                    <li class="<c:if test="${menuItem.startsWith('about')}">active</c:if> dropdown">
                        <a class="dropdown-toggle" href="#">
                            关于我们
                            <i class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0)">关于古奥</a></li>
                            <li><a href="javascript:void(0)">企业文化</a></li>
                            <li><a href="javascript:void(0)">团队介绍</a></li>
                        </ul>
                    </li>
                    <li class="<c:if test="${menuItem.startsWith('contact')}">active</c:if>">
                        <a href="#">
                            联系我们
                        </a>
                    </li>--%>
                    <li class="<c:if test="${menuItem.startsWith('login')}">active</c:if>">
                        <a href="<dms:path/>/login">
                            登录/注册
                        </a>
                    </li>
                    <%--<li class="dropdown mega-menu-item mega-menu-signin signin" id="headerAccount">
                        <a class="dropdown-toggle" href="page-login.html">
                            <i class="fa fa-user"></i> 用户登录
                            <i class="fa fa-angle-down"></i>
                        </a>
                        &lt;%&ndash;<ul class="dropdown-menu">
                            <li>
                                <div class="mega-menu-content">
                                    <div class="row">
                                        <div class="col-md-12">

                                            <div class="signin-form">

                                                <span class="mega-menu-sub-title">用户登录</span>

                                                <form id="login_form">
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <label>邮箱地址</label>
                                                                <input name="userName" type="text" value="caocs@163.com" class="form-control input-lg">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <a class="pull-right" id="headerRecover" href="#">(忘记密码?)</a>
                                                                <label>密码</label>
                                                                <input name="password" type="password" value="123456" class="form-control input-lg">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <span class="remember-box checkbox">
                                                                <label for="rememberme">
                                                                    <input type="checkbox" id="rememberme" name="rememberme">下次自动登录
                                                                </label>
                                                            </span>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="submit" value="登录" class="btn btn-primary pull-right push-bottom" data-loading-text="Loading...">
                                                        </div>
                                                    </div>
                                                </form>

                                                <p class="sign-up-info">还没有账号? <a href="#" id="headerSignUp">免费注册</a></p>

                                            </div>

                                            <div class="signup-form">
                                                <span class="mega-menu-sub-title">快速注册</span>

                                                <form id="register_form">
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <label>邮箱地址</label>
                                                                <input name="userName" type="text" value="" class="form-control input-lg">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-6">
                                                                <label>密码</label>
                                                                <input id="register_password" name="password" type="password" value="" class="form-control input-lg">
                                                            </div>
                                                            <div class="col-md-6">
                                                                <label>密码确认</label>
                                                                <input name="passwordConfirm" type="password" value="" class="form-control input-lg">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <input type="submit" value="注册" class="btn btn-primary pull-right push-bottom" data-loading-text="Loading...">
                                                        </div>
                                                    </div>
                                                </form>

                                                <p class="log-in-info">已有账户? <a href="#" id="headerSignIn">直接登录</a></p>
                                            </div>

                                            <div class="recover-form">
                                                <span class="mega-menu-sub-title">重设密码</span>
                                                <p>请填写您注册时使用的Email地址，系统会发送密码重设邮件，请注意查收.</p>

                                                <form action="" id="" method="post">
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <label>邮箱地址</label>
                                                                <input type="text" value="" class="form-control input-lg">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <input type="submit" value="发送邮件" class="btn btn-primary pull-right push-bottom" data-loading-text="Loading...">
                                                        </div>
                                                    </div>
                                                </form>

                                                <p class="log-in-info"><a href="#" id="headerRecoverCancel">返回登录</a></p>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>&ndash;%&gt;
                    </li>--%>
                </ul>
            </nav>
        </div>
    </div>
</header>