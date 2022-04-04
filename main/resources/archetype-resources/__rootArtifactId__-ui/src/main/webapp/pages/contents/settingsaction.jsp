#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag"%>

<div class="row">
	<div class="col-12 text-center">
		<c:choose>
			<c:when test="${symbol_dollar}{not empty requestScope.user.image}">
				<img src="${symbol_dollar}{requestScope.user.image}" style="width: 100%; height: 100%; max-width: 200px;" />
			</c:when>
			<c:otherwise>
				<img src="images/user-large.png?<${parentArtifactId}:currentDate />" style="width: 100%; height: 100%; max-width: 200px;" />
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="row">
	<div class="col-12 text-center">
		<h4 class="heading"><c:out value="${symbol_dollar}{requestScope.user.displayName}" /></h4>
	</div>
</div>

<c:choose>
	<c:when test="${symbol_dollar}{sessionScope.account_type == 'ADMIN'}"> 
		<c:choose>
			<c:when test="${symbol_dollar}{not requestScope.user.systemUser}"> 
				<div class="row"><div class="col-12"><a href="settings/user/profile"><span>Profile</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/photo"><span>Photo</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/account"><span>Account Settings</span></a></div></div>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${symbol_dollar}{sessionScope.account_type == 'ROOT'}">
		<c:choose>
			<c:when test="${symbol_dollar}{not requestScope.user.systemUser}"> 
				<div class="row"><div class="col-12"><a href="settings/user/profile"><span>Profile</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/photo"><span>Photo</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/account"><span>Account Settings</span></a></div></div>
			</c:when>
		</c:choose>
		<div class="row"><div class="col-12"><a href="settings/user/delete"><span>Delete Account</span></a></div></div> 
	</c:when>
	<c:when test="${symbol_dollar}{sessionScope.account_type == 'MEMBER'}"> 
		<c:choose>
			<c:when test="${symbol_dollar}{not requestScope.user.systemUser}"> 
				<div class="row"><div class="col-12"><a href="settings/user/profile"><span>Profile</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/photo"><span>Photo</span></a></div></div>
				<div class="row"><div class="col-12"><a href="settings/user/account"><span>Account Settings</span></a></div></div>
			</c:when>
		</c:choose>
		<div class="row"><div class="col-12"><a href="settings/user/delete"><span>Delete Account</span></a></div></div> 
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
