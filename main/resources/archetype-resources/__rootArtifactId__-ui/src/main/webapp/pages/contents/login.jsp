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
			<form id="loginForm" method="post" action="login">
				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-12 form-label text-center">${name}</div>
				</div>
				<div class="row">
					<div class="col-12 form-label text-center">Login to your account</div>
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
					<label for="loginEmail">Email or Username</label>
					<input id="loginEmail" type="text" name="email" 
						   class="form-control validate" required="required"
						   value="${symbol_dollar}{requestScope.email}" autofocus="autofocus"/>
				</div>
				<div class="form-group row">
					<label for="loginPassword">Password</label>
					<input id="loginPassword" type="password" name="password" 
						   class="form-control validate" required="required" />
				</div>
				<div class="row">
					<div class="form-group form-check col">
					    <input type="checkbox" class="form-check-input" id="chkRemember" >
					    <label class="form-check-label" for="chkRemember">Remember me</label>
					</div>
					<div class="col" style="text-align: right; font-weight: bold;">
						<c:if test="${symbol_dollar}{sessionScope.login_attempt >= 2}">
							<span class="pointer"><a href="forgotpassword">Oh no! I forgot my password!</a></span>
						</c:if>
					</div>
				</div>
				
				<div class="form-group row">
					<input id="redirectUrl" type="hidden" name="redirectUrl" value="${symbol_dollar}{requestScope.redirectUrl}" />
					<button class="form-control btn btn-primary pointer" type="submit" id="btnLoginForm" name="action">Login</button>
				</div>
			</form>
			<br />
		</div>
		<div class="col">&nbsp;</div>
	</div>
	<div class="col-md-12" style="text-align: center">
		Haven't registered yet? <br /> <span style="font-weight: bold;"><a href="register">Create an account</a></span> and spread your wings!
	</div>
</div>
<!-- END:   MAIN -->