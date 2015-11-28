<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/ga-tags" prefix="ga"%>
<!doctype html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <meta name="keywords" content="基因" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <tiles:insertTemplate template="../sections/home_styles.jsp" />
    <tilesx:useAttribute id="styleList" name="styles" classname="java.util.List" /><c:forEach var="item" items="${styleList}">
    <link href="<ga:path/>${item}" rel="stylesheet" type="text/css"></c:forEach>
    <title><tiles:insertAttribute name="title" /></title>
</head>
<body class="page-header-fixed">
<script>var app_inits = [];</script>
<div class="wrapper">

    <header class="page-header">
        <nav class="navbar mega-menu" role="navigation">
            <div class="container-fluid">
                <tiles:insertAttribute name="header" />

                <tiles:insertAttribute name="menu" />
            </div>
            <!--/container-->
        </nav>
    </header>

    <div class="container-fluid">
        <div class="page-content">
            <tiles:insertAttribute name="content" />
        </div>
    </div>

    <tiles:insertAttribute name="footer" />
</div>

<tiles:insertTemplate template="../sections/home_scripts.jsp" /><tilesx:useAttribute id="scriptList" name="scripts" classname="java.util.List" /><c:forEach var="item" items="${scriptList}">
<c:if test="${fn:startsWith(item,'/')}"><script src="<ga:path/>${item}"></script></c:if><c:if test="${!fn:startsWith(item,'/')}"><script src="${item}"></script></c:if></c:forEach>
<script>
    $(function(){
        Metronic.init();
        Layout.init();

        $app.init({contextRootUrl:'<ga:path/>'});
    })
</script>
</body>
</html>