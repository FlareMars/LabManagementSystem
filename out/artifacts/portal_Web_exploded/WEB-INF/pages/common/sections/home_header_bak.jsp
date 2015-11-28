<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/ga-tags" prefix="ga"%>
<header class="header">
    <div class="logo-container">
        <a href="<ga:path/>/home" class="logo">
            <img src="<ga:path/>/assets/home/images/admin_logo.png" height="35" alt="Porto Admin" />
        </a>
    </div>

    <!-- start: search & user box -->
    <div class="header-left">
        <span class="separator"></span>
        <div class="header-nav">
            <a href="<ga:path/>/home"><i class="fa fa-home"></i>&nbsp;&nbsp;首页 </a>
        </div>
        <span class="separator"></span>

        <div class="header-nav">
            <a href="javascript:void(0)" data-toggle="dropdown"><i class="fa fa-th-large"></i>&nbsp;&nbsp;项目 &nbsp;&nbsp;<i class="fa custom-caret"></i></a>

            <div class="dropdown-menu">
                <ul class="list-unstyled">
                    <li>
                        <a role="menuitem" tabindex="-1" href="<ga:path/>/project/list"><i class="fa fa-user"></i>&nbsp;&nbsp;我的项目</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a role="menuitem" tabindex="-1" href="<ga:path/>/pipeline/list"><i class="fa fa-plus"></i>&nbsp;&nbsp;创建项目</a>
                    </li>
                </ul>
            </div>
        </div>
        <span class="separator"></span>

        <div class="header-nav">
            <a href="javascript:void(0)" data-toggle="dropdown"><i class="fa fa-folder"></i>&nbsp;&nbsp;文件 &nbsp;&nbsp;<i class="fa custom-caret"></i></a>

            <div class="dropdown-menu">
                <ul class="list-unstyled">
                    <li>
                        <a tabindex="-1" href="<ga:path/>/file/dir"><i class="fa fa-files-o"></i>&nbsp;&nbsp;我的文件</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a tabindex="-1" href="javascript:void(0)"><i class="fa fa-cloud"></i>&nbsp;&nbsp;我的百度云</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a tabindex="-1" href="javascript:void(0)"><i class="fa fa-database"></i>&nbsp;&nbsp;公共文件</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a tabindex="-1" href="javascript:void(0)"><i class="fa fa-cloud-upload"></i>&nbsp;&nbsp;导入文件</a>
                    </li>
                </ul>
            </div>
        </div>
        <span class="separator"></span>

        <div class="header-nav">
            <a href="javascript:void(0)" data-toggle="dropdown"><i class="fa fa-random"></i>&nbsp;&nbsp;Pipelines &nbsp;&nbsp;<i class="fa custom-caret"></i></a>

            <div class="dropdown-menu">
                <ul class="list-unstyled">
                    <li>
                        <a role="menuitem" tabindex="-1" href="<ga:path/>/pipeline/detail?type=code&key=rna"><i class="fa fa-user"></i>&nbsp;&nbsp;RNA流程分析</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a role="menuitem" tabindex="-1" href="<ga:path/>/pipeline/list"><i class="fa fa-list"></i>&nbsp;&nbsp;所有流程</a>
                    </li>
                </ul>
            </div>
        </div>
        <span class="separator"></span>
    </div>
    <!-- start: search & user box -->
    <div class="header-right">

        <form action="pages-search-results.html" class="search nav-form">
            <div class="input-group input-search">
                <input type="text" class="form-control" name="q" id="q" placeholder="Search...">
							<span class="input-group-btn">
								<button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
							</span>
            </div>
        </form>

        <span class="separator"></span>

        <ul class="notifications">
            <li>
                <a href="#" class="dropdown-toggle notification-icon" title="通知" data-toggle="dropdown">
                    <i class="fa fa-tasks"></i>
                    <span class="badge">3</span>
                </a>

                <div class="dropdown-menu notification-menu large">
                    <div class="notification-title">
                        <span class="pull-right label label-default">3</span>
                        Tasks
                    </div>

                    <div class="content">
                        <ul>
                            <li>
                                <p class="clearfix mb-xs">
                                    <span class="message pull-left">Generating Sales Report</span>
                                    <span class="message pull-right text-dark">60%</span>
                                </p>
                                <div class="progress progress-xs light">
                                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;"></div>
                                </div>
                            </li>

                            <li>
                                <p class="clearfix mb-xs">
                                    <span class="message pull-left">Importing Contacts</span>
                                    <span class="message pull-right text-dark">98%</span>
                                </p>
                                <div class="progress progress-xs light">
                                    <div class="progress-bar" role="progressbar" aria-valuenow="98" aria-valuemin="0" aria-valuemax="100" style="width: 98%;"></div>
                                </div>
                            </li>

                            <li>
                                <p class="clearfix mb-xs">
                                    <span class="message pull-left">Uploading something big</span>
                                    <span class="message pull-right text-dark">33%</span>
                                </p>
                                <div class="progress progress-xs light mb-xs">
                                    <div class="progress-bar" role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width: 33%;"></div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" class="dropdown-toggle notification-icon" data-toggle="dropdown">
                    <i class="fa fa-envelope"></i>
                    <span class="badge">4</span>
                </a>

                <div class="dropdown-menu notification-menu">
                    <div class="notification-title">
                        <span class="pull-right label label-default">230</span>
                        Messages
                    </div>

                    <div class="content">
                        <ul>
                            <li>
                                <a href="#" class="clearfix">
                                    <figure class="image">
                                        <img src="assets/images/!sample-user.jpg" alt="Joseph Doe Junior" class="img-circle" />
                                    </figure>
                                    <span class="title">Joseph Doe</span>
                                    <span class="message">Lorem ipsum dolor sit.</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="clearfix">
                                    <figure class="image">
                                        <img src="assets/images/!sample-user.jpg" alt="Joseph Junior" class="img-circle" />
                                    </figure>
                                    <span class="title">Joseph Junior</span>
                                    <span class="message truncate">Truncated message. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sit amet lacinia orci. Proin vestibulum eget risus non luctus. Nunc cursus lacinia lacinia. Nulla molestie malesuada est ac tincidunt. Quisque eget convallis diam, nec venenatis risus. Vestibulum blandit faucibus est et malesuada. Sed interdum cursus dui nec venenatis. Pellentesque non nisi lobortis, rutrum eros ut, convallis nisi. Sed tellus turpis, dignissim sit amet tristique quis, pretium id est. Sed aliquam diam diam, sit amet faucibus tellus ultricies eu. Aliquam lacinia nibh a metus bibendum, eu commodo eros commodo. Sed commodo molestie elit, a molestie lacus porttitor id. Donec facilisis varius sapien, ac fringilla velit porttitor et. Nam tincidunt gravida dui, sed pharetra odio pharetra nec. Duis consectetur venenatis pharetra. Vestibulum egestas nisi quis elementum elementum.</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="clearfix">
                                    <figure class="image">
                                        <img src="assets/images/!sample-user.jpg" alt="Joe Junior" class="img-circle" />
                                    </figure>
                                    <span class="title">Joe Junior</span>
                                    <span class="message">Lorem ipsum dolor sit.</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="clearfix">
                                    <figure class="image">
                                        <img src="assets/images/!sample-user.jpg" alt="Joseph Junior" class="img-circle" />
                                    </figure>
                                    <span class="title">Joseph Junior</span>
                                    <span class="message">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sit amet lacinia orci. Proin vestibulum eget risus non luctus. Nunc cursus lacinia lacinia. Nulla molestie malesuada est ac tincidunt. Quisque eget convallis diam.</span>
                                </a>
                            </li>
                        </ul>

                        <hr />

                        <div class="text-right">
                            <a href="#" class="view-more">View All</a>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <a href="#" class="dropdown-toggle notification-icon" data-toggle="dropdown">
                    <i class="fa fa-bell"></i>
                    <span class="badge">3</span>
                </a>

                <div class="dropdown-menu notification-menu">
                    <div class="notification-title">
                        <span class="pull-right label label-default">3</span>
                        Alerts
                    </div>

                    <div class="content">
                        <ul>
                            <li>
                                <a href="#" class="clearfix">
                                    <div class="image">
                                        <i class="fa fa-thumbs-down bg-danger"></i>
                                    </div>
                                    <span class="title">Server is Down!</span>
                                    <span class="message">Just now</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="clearfix">
                                    <div class="image">
                                        <i class="fa fa-lock bg-warning"></i>
                                    </div>
                                    <span class="title">User Locked</span>
                                    <span class="message">15 minutes ago</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="clearfix">
                                    <div class="image">
                                        <i class="fa fa-signal bg-success"></i>
                                    </div>
                                    <span class="title">Connection Restaured</span>
                                    <span class="message">10/10/2014</span>
                                </a>
                            </li>
                        </ul>

                        <hr />

                        <div class="text-right">
                            <a href="#" class="view-more">View All</a>
                        </div>
                    </div>
                </div>
            </li>
        </ul>

        <span class="separator"></span>

        <div id="userbox" class="userbox">
            <a href="#" data-toggle="dropdown">
                <figure class="profile-picture">
                    <img src="<ga:path/>/assets/home/images/def-user-header.jpg" alt="Joseph Doe" class="img-circle" />
                </figure>
                <div class="profile-info">
                    <span class="name">Timmy</span>
                    <span class="role">普通用户</span>
                </div>

                <i class="fa custom-caret"></i>
            </a>

            <div class="dropdown-menu">
                <ul class="list-unstyled">
                    <li class="divider"></li>
                    <li>
                        <a role="menuitem" tabindex="-1" href="javascript:void(0)"><i class="fa fa-user"></i> 个人信息</a>
                    </li>
                    <li>
                        <a role="menuitem" tabindex="-1" href="javascript:void(0)"><i class="fa fa-power-off"></i> 注销</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end: search & user box -->
</header>