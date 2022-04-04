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
			<div class="col-md-12 text-center">
				<h3 class="heading">Profile Picture</h3>
				<h5>Add your latest photo. Image size should not be more than 500KB.</h5>
			</div>
		</div>
		<form method="post" action="settings/user/photo">
			<div class="row">
				<div class="col-3">&nbsp;</div>
				<div class="col-6">
					<div class="form-group"> 
						<div class="img-picker" data-name="image" data-value="${symbol_dollar}{requestScope.user.image}"></div>
					</div>
				</div>
				<div class="col-3">&nbsp;</div>
			</div>
			<div class="row">
				<button type="submit" class="btn btn-primary btn-block" name="action">Save</button>
			</div>
		</form>
	</div>
	<div class="col-6">&nbsp;</div>
</div>