#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag"%>

<!-- START: MAIN -->
<div class="container">
	<div class="row">
		<div class="col">&nbsp;</div>
		<div class="col-6">
			<form id="signupForm" method="post" action="register">
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-12 form-label text-center">Register</div>
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
					<label for="registerFName">First Name *</label>
					<input id="registerFName" type="text" name="first_name"
						class="form-control validate" required="required"
						value="${symbol_dollar}{requestScope.first_name}" autofocus="autofocus"/> 
				</div>
				
				<div class="form-group row">
					<label for="registerLName">Last Name *</label>
					<input id="registerLName" type="text" name="last_name"
						class="form-control validate" required="required"
						value="${symbol_dollar}{requestScope.last_name}"/> 
				</div>
				
				<div class="form-group row">
					<label for="registerEmail">Email *</label>
					<input id="registerEmail" type="email" name="email"
						class="form-control validate" required="required"
						value="${symbol_dollar}{requestScope.email}"/> 
				</div>
				
				<div class="form-group row">
					<label for="registerPassword">Password *</label>
					<input id="registerPassword" type="password" name="password"
						class="form-control validate" required="required"/> 
				</div>
				
				<div class="form-group row">
					<label for="registerCPassword">Confirm Password *</label>
					<input id="registerCPassword" type="password" name="confirm_password"
						class="form-control validate" required="required"/> 
				</div>
				
				<div class="form-group form-check row">
				    <input type="checkbox" class="form-check-input" id="registerPolicy" >
				    <label class="form-check-label" for="registerPolicy">I agree to the <a href="${symbol_pound}">terms and conditions</a>.</label>
				</div>
				
				<div class="form-group row">
					<button class="form-control btn btn-primary pointer" type="submit" id="registerSubmitBtn" name="action">Login</button>
				</div>
			</form>
		</div>
		<div class="col">&nbsp;</div>
	</div>
</div>
<!-- END:   MAIN -->