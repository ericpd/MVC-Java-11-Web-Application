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
		<div class="row">
			<div class="col-12 text-center">
				<h3 class="heading">Delete Account</h3>
				<h5>Delete your account permanently.</h5>
			</div>
		</div>
		
		<div class="row">&nbsp;</div>
		<div class="row">&nbsp;</div>
		
		<div class="row">
			<div class="col-12">
				<p>Delete your Account</p>
			</div>
		</div>
		
		<div class="row">&nbsp;</div>
		
		<div class="row">
			<div class="col-12">
				<p><span class="text-danger">Warning: </span>This action will permanently delete your account in our system</p>
			</div>
		</div>
		
		<div class="row">&nbsp;</div>
		
		<div class="row">
			<div class="col-12 text-right">
				<form method="post" action="settings/user/delete">
					<input type="submit" class="btn btn-danger" value="Delete Account"/>
				</form>
			</div>
		</div>
	</div>
	<div class="col-6">&nbsp;</div>
</div>

