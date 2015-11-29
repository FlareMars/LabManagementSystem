<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/ga-tags" prefix="ga"%>

<div class="nav-collapse collapse navbar-collapse navbar-responsive-collapse">
    <ul class="nav navbar-nav text-uppercase">
        <li class="dropdown dropdown-fw <c:if test="${_menuId.startsWith('home')}">open selected</c:if>">
            <a href="<ga:path/>/home">
                首页
            </a>
            <ul class="dropdown-menu dropdown-menu-fw">
                <li class="active"><a href="javascript:;">首页</a></li>
            </ul>
        </li>
        <li class="dropdown dropdown-fw <c:if test="${_menuId.startsWith('project')}">open selected</c:if>">
            <a href="<ga:path/>/project/list">
                我的项目
            </a>
            <ul class="dropdown-menu dropdown-menu-fw">
                <li class="<c:if test="${_menuId.startsWith('project.list')}">active</c:if>"><a href="<ga:path/>/project/list">浏览我的项目</a></li>
                <li class="<c:if test="${_menuId.startsWith('project.create')}">active</c:if>"><a href="<ga:path/>/project/create">创建新项目</a></li>
            </ul>
        </li>
        <li class="dropdown dropdown-fw <c:if test="${_menuId.startsWith('file')}">open selected</c:if>">
            <a href="<ga:path/>/file/dir">
                云存储
            </a>
            <ul class="dropdown-menu dropdown-menu-fw">
                <li class="<c:if test="${_menuId.startsWith('file.list')}">active</c:if>"><a href="<ga:path/>/file/dir">浏览我的文件</a></li>
            </ul>
        </li>
        <li class="dropdown dropdown-fw <c:if test="${_menuId.startsWith('flow')}">open selected</c:if>">
            <a href="<ga:path/>/flows/rna">
                分析流程
            </a>
            <ul class="dropdown-menu dropdown-menu-fw">
                <li class="<c:if test="${_menuId.startsWith('flow.rna')}">active</c:if>"><a href="<ga:path/>/flows/rna">RNA分析流程</a></li>
                <li class="<c:if test="${_menuId.startsWith('flow.dna')}">active</c:if>"><a href="<ga:path/>/flows/dna">DNA分析流程</a></li>
            </ul>
        </li>
        <%--<li class="dropdown dropdown-fw <c:if test="${_menuId.startsWith('database')}">open selected</c:if>">
            <a href="<ga:path/>/file/database">
                数据库
            </a>
            <ul class="dropdown-menu dropdown-menu-fw">
                <li class="active"><a href="javascript:void(0)">数据库</a></li>
            </ul>
        </li>--%>
    </ul>
</div>