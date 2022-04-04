#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag"%>

<div class="row">&nbsp;</div>
<div class="row">
	<div class="col-2">
		<jsp:include page="settingsaction.jsp" />
	</div>
	<div class="col-4">	
		<form method="post" action="settings/user/account">			
			<div class="row">
				<div class="col-12 text-center">
					<h3 class="heading">Account settings</h3>
					<h5>Change your password here.</h5>
				</div>
			</div>
			<div class="row">&nbsp;</div>
											
			<c:if test="${symbol_dollar}{not empty requestScope.error_message}">
				<div class="row text-center">
					<label class="text-danger">
						<c:out value="${symbol_dollar}{requestScope.error_message}" />
					</label>
				</div>
				<div class="row">&nbsp;</div>
			</c:if>
				
			<div class="form-group row">
				<label for="userEmail">Email Address</label>
				<input id="userEmail" type="email" name="email"
					class="form-control validate" required="required"
					value="${symbol_dollar}{requestScope.user.email}"/> 
			</div>
				
			<div class="form-group row">
				<label for="userPassword">Current Password</label>
				<input id="userPassword" type="password" name="password"
					class="form-control validate" required="required"/> 
			</div>
			
			<div class="form-group row">
				<label for="userNewPassword">New Password</label>
				<input id="userNewPassword" type="password" name="new_password"
					class="form-control validate" required="required"/> 
			</div>
			
			<div class="form-group row">
				<label for="userCPassword">Confirm Password</label>
				<input id="userCPassword" type="password" name="confirm_password"
					class="form-control validate" required="required"/> 
			</div>
			
			<div class="row">&nbsp;</div>				
			<div class="form-group row">
				<button class="form-control btn btn-primary pointer" type="submit" name="action">Update Account</button>
			</div>			
		</form>
	</div>
	<div class="col-6">&nbsp;</div>
</div>