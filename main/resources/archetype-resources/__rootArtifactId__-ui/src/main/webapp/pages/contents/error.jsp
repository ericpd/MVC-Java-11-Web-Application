#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag"%>


<center style="padding:200px;">
	<h1 style="color: red;">Error</h1>
	<br />
	<span>
		<c:choose>
			<c:when test="${symbol_dollar}{not empty sessionScope and not empty sessionScope.status}">
				<c:out value="${symbol_dollar}{sessionScope.status}" />
			</c:when>
			<c:otherwise>500</c:otherwise>
		</c:choose>
	</span>
	<br />
	<span>
		<c:choose>
			<c:when test="${symbol_dollar}{not empty sessionScope and not empty sessionScope.message}">
				<c:out value="${symbol_dollar}{sessionScope.message}" />
			</c:when>
			<c:otherwise>Internal system error. Please contact support@kowdtech.com.</c:otherwise>
		</c:choose>
	</span>
</center>