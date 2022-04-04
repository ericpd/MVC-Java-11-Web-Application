#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag" %>
<%@ taglib prefix="template" uri="templateTag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="${symbol_dollar}{requestScope.modifiedRequestURL}${symbol_dollar}{pageContext.request.contextPath}/" />

	<title><${parentArtifactId}:message key="Application.Title"/></title>
	
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	
	<link rel="shortcut icon" href="favicon.ico" />
	
	<%-- BOOTSTRAP CSS --%>
  	<link href="assets/vendor/bootstrap/css/bootstrap.min.css?<${parentArtifactId}:currentDate />" rel="stylesheet" media="screen,projection" />
	
	<%--TODO: add other main styles here--%>
	<link rel="stylesheet" type="text/css" href="styles/${parentArtifactId}.css?<${parentArtifactId}:currentDate />" media="screen,projection" />
	<link rel="stylesheet" type="text/css" href="styles/common.css?<${parentArtifactId}:currentDate />" media="screen,projection" />
	<%-- Custom page styles --%>
	<template:get name='styles'/>
</head>

<body>
	<div id="wrapper">
		<%-- Page Content --%>
		<template:get name='content'/>
	</div>
	
	<jsp:include page="../pages/scripts/common.jsp" />
	<script type="text/javascript" src="scripts/common.js?<${parentArtifactId}:currentDate />"></script>
	<template:get name='scripts'/>
</body>
</html>