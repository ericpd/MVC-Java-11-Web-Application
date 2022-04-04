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
		<form method="post" action="settings/user/profile">		
			<div class="row">
				<div class="col-12 text-center">
					<h3 class="heading">Profile</h3>
					<h5>Add or update information about yourself here.</h5>
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
				<label for="userFirstName">First Name</label>
				<input id="regisuserFirstNameterEmail" type="text" name="firstName"
					class="form-control validate" required="required"
					value="${symbol_dollar}{requestScope.user.firstName}"/> 
			</div>
				
			<div class="form-group row">
				<label for="userLastName">Last Name</label>
				<input id="userLastName" type="text" name="lastName"
					class="form-control validate" required="required"
					value="${symbol_dollar}{requestScope.user.lastName}"/> 
			</div>
			
			<div class="row">&nbsp;</div>				
			<div class="form-group row">
				<button class="form-control btn btn-primary pointer" type="submit" name="action">Update Account</button>
			</div>		
		</form>
	</div>
	<div class="col-6">&nbsp;</div>
</div>
