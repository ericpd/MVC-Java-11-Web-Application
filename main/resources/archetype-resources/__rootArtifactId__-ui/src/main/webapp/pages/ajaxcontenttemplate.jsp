#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="template" uri="templateTag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:insert template='/templates/ajaxcontenttemplate.jsp'>
	<template:put name='styles' content='/pages/styles/${symbol_dollar}{requestScope.targetJsp}.jsp'/>
	<template:put name='content' content='/pages/contents/${symbol_dollar}{requestScope.targetJsp}.jsp'/>
	<template:put name='scripts' content='/pages/scripts/${symbol_dollar}{requestScope.targetJsp}.jsp'/>
</template:insert>
