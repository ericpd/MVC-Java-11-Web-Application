#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="template" uri="templateTag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Custom page styles --%>
<template:get name='styles'/>
		
<%-- Page Content --%>
<template:get name='content'/>
		
<%-- Custom page scripts --%>
<template:get name='scripts'/>