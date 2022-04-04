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
  	<link href="assets/vendor/simple-sidebar/css/simple-sidebar.css?<${parentArtifactId}:currentDate />" rel="stylesheet" media="screen,projection" />
	
	<%--TODO: add other main styles here--%>
	<link rel="stylesheet" type="text/css" href="styles/${parentArtifactId}.css?<${parentArtifactId}:currentDate />" media="screen,projection" />
	<link rel="stylesheet" type="text/css" href="styles/common.css?<${parentArtifactId}:currentDate />" media="screen,projection" />
	<%-- Custom page styles --%>
	<template:get name='styles'/>
</head>

<body>
	<div class="d-flex" id="wrapper">
	
		<!-- Sidebar -->
		<div class="bg-light border-right" id="sidebar-wrapper">
			<div class="sidebar-heading text-center">
				${name}
			</div>
			<div class="sidebar-version text-center">
				<${parentArtifactId}:message key="Application.Heartbeat"/>
			</div>
			<br>
			<div class="list-group list-group-flush">
				<${parentArtifactId}:menuItems/>
			</div>
		</div>
		<!-- /${symbol_pound}sidebar-wrapper -->

		<div id="page-content-wrapper">
			<%-- HEADER - START --%>
			<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
				<button class="btn btn-primary" id="menu-toggle">Toggle Menu</button>
			
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="${symbol_pound}navbarSupportedContent" aria-controls="navbarSupportedContent"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
			
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item active">
							<a class="nav-link" href="home">Home <span class="sr-only">(current)</span></a>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="${symbol_pound}" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<c:choose>
									<c:when test="${symbol_dollar}{not empty sessionScope.image}"> 
										<img src="${symbol_dollar}{sessionScope.image}" class="img-rounded" style="max-width: 24px"> <i class="icon-submenu icon-chevron-down"></i>
									</c:when>
									<c:otherwise>
										<img src="images/user.png?<${parentArtifactId}:currentDate />" class="img-circle" style="max-width: 24px"> <i class="icon-submenu icon-chevron-down"></i>
									</c:otherwise>
								</c:choose>
							</a>
							<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="settings">Settings</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="logout">Logout</a>
							</div>
						</li>
					</ul>
				</div>
			</nav>
			<%-- HEADER - END --%>
			
			<%-- Page Content --%>
			<div class="container-fluid">
				<template:get name='content'/>
			</div>
		</div>
	</div>
	
	<jsp:include page="/pages/contents/modalconfirmation.jsp" />
	
	<jsp:include page="../pages/scripts/common.jsp" />
	<script type="text/javascript" src="scripts/common.js?<${parentArtifactId}:currentDate />"></script>
	<template:get name='scripts'/>

	<script type="text/javascript">
		var toastMessages = ${symbol_dollar}{requestScope.toastMessage};
		
		if (toastMessages.length > 0) {
			for (var i = 0; i < toastMessages.length; i++) {
				var toastType = toastMessages[i].m_type;
				var toastMessage = toastMessages[i].m_message;	
	
				showMessage(toastType, toastMessage);
			}
		}
	</script>
</body>
</html>