<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/ga-tags" prefix="ga"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="基因" />
    <meta name="description" content="Porto - Responsive HTML5 Template">
    <tiles:insertTemplate template="../sections/default_styles.jsp" />
    <tilesx:useAttribute id="styleList" name="styles" classname="java.util.List" />
    <c:forEach var="item" items="${styleList}">
        <link href="<ga:path/>${item}" rel="stylesheet" type="text/css">
    </c:forEach>
    <title><tiles:insertAttribute name="title" /></title>
    <!--[if lte IE 8]>
    <script src="<ga:path/>/assets/default/lib/respond/respond.js"></script>
    <script src="<ga:path/>/assets/default/lib/excanvas/excanvas.js"></script>
    <![endif]-->
</head>
<body>
<script>var app_inits = [];</script>
<div class="body">
    <tiles:insertAttribute name="header" />
    <div role="main" class="main">
        <tiles:insertAttribute name="content" />
    </div>
    <tiles:insertAttribute name="footer" />
</div>
<tiles:insertTemplate template="../sections/default_scripts.jsp" />
<tilesx:useAttribute id="scriptList" name="scripts" classname="java.util.List" />
<c:forEach var="item" items="${scriptList}">
    <c:if test="${fn:startsWith(item,'/')}">
        <script src="<ga:path/>${item}"></script>
    </c:if>
    <c:if test="${!fn:startsWith(item,'/')}">
        <script src="<c:out value="${item}"/>"></script>
    </c:if>
</c:forEach>
<script>
    $(function(){
        $app.init({contextRootUrl:'<ga:path/>'});
    })
</script>
</body>
</html>