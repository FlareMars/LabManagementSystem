<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/ga-tags" prefix="ga"%>

<!-- Docs master nav -->
<header class="navbar navbar-static-top bs-docs-nav" id="top"
        role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button"
                    data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a href="<ga:path />/" class="navbar-brand">GeneApps</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse"
             role="navigation">
            <!--
            <ul class="nav navbar-nav">
                <li><a href="<ga:path />/cataloginfo">首页</a></li>
                <li><a href="<ga:path />/stepinfo">服务介绍</a></li>
                <li><a href="<ga:path />/pricePlan">服务流程</a></li>
                <li><a href="<ga:path />/gene/browser">基因浏览器</a></li>
                <li><a href="<ga:path />/aboutUs">关于我们</a></li>
            </ul>-->
            <ul class="nav navbar-nav navbar-right">
                    <li><a href="http://report.geneapps.cn/${analyses.id}.zip"
                           target="_blank">下载概述报告</a>
                    </li>
                <li><a href="<ga:path />/home">返回我的工作区</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
