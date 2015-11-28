<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="/ga-tags" prefix="ga"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><tiles:insertAttribute name="title" /></title>

    <!-- Bootstrap core CSS -->
<!--
    <link rel="shortcut icon" href="<ga:path />/assets/report/images/icon/icon-logo.ico">-->
    <link href="<ga:path />/assets/report/framework/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="<ga:path />/assets/report/css/patch.css" rel="stylesheet">

    <!-- Documentation extras -->
    <link href="<ga:path />/assets/report/css/docs.min.css" rel="stylesheet">

    <script src="<ga:path />/assets/report/js/lib/jquery.js"></script>
    <script src="<ga:path />/assets/report/framework/bootstrap/js/bootstrap.min.js"></script>
    <script src="<ga:path />/assets/report/js/docs.min.js"></script>
    <!--[if lt IE 9]><script src="<ga:path />/assets/report/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<ga:path />/assets/report/js/ie10-viewport-bug-workaround.js"></script>
    <script src="<ga:path />/assets/report/js/ie-emulation-modes-warning.js"></script>
    <script src="<ga:path />/assets/report/js/lib/mybootstrap.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="<ga:path />/assets/report/js/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<tiles:insertAttribute name="header" />

<tiles:insertAttribute name="content" />

<!-- Footer
================================================== -->
<tiles:insertAttribute name="footer" />

</body>
</html>

