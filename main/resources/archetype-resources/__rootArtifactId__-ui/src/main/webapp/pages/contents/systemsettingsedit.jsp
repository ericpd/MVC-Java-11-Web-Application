#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag"%>

<div class="main">
	<!-- MAIN CONTENT -->
	<div class="main-content">
		<div class="container-fluid">
			<div class="panel">
				<div class="panel-heading">
					<div class="row">
						<div class="col-md-12">
							<h2 class="heading">
								System Settings Edit
								<c:if test="${symbol_dollar}{not edit}">
									<a id="editSetting" class="btn-edit right" type="button" data-id="${symbol_dollar}{setting.id}" data-encrypted="${symbol_dollar}{setting.encrypted}">
										<i class="icon-pencil"></i>
									</a>
								</c:if>
							</h2>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<c:choose>
						<c:when test="${symbol_dollar}{edit}">
							<c:if test="${symbol_dollar}{not empty error_message}">
								<div class="alert alert-danger alert-dismissible" role="alert">
									<button class="close" aria-label="Close" type="button" data-dismiss="alert"><span aria-hidden="true">�</span></button>
									<i class="icon-alarm-error"></i> <c:out value="${symbol_dollar}{error_message}" />
								</div>
							</c:if>
							<form action="systemsettings/edit" method="post">
								<table class="table table-bordered">
									<tbody>										
										<tr>
											<td>ID</td>
											<td><c:out value="${symbol_dollar}{setting.id}" /></td>
										</tr>
										<tr>
											<td>Name</td>
											<td><c:out value="${symbol_dollar}{setting.name}" /></td>
										</tr>
										<tr>
											<td>Value</td>
											<td>
												<input type="hidden" value="${symbol_dollar}{setting.id}" name="id"/>
												<input class="form-control input-sm" required="required" type="text" value="${symbol_dollar}{setting.value}" name="value"/>
											</td>
										</tr>
										<tr>
											<td>Encrypted</td>
											<td>
												<c:choose>
													<c:when test="${symbol_dollar}{setting.encrypted}">
														<input type="checkbox" name="encrypted" checked/>
													</c:when>
													<c:otherwise>
														<input type="checkbox" name="encrypted"/>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</tbody>
								</table>
								<div>
									<input class="form-control btn-primary" type="submit" value="Save"/>
								</div>
							</form>
						</c:when>
						<c:otherwise>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td>ID</td>
										<td><c:out value="${symbol_dollar}{setting.id}" /></td>
									</tr>
									<tr>
										<td>Name</td>
										<td><c:out value="${symbol_dollar}{setting.name}" /></td>
									</tr>
									<tr>
										<td>Value</td>
										<td><c:out value="${symbol_dollar}{setting.value}" /></td>
									</tr>
									<tr>
										<td>Encrypted</td>
										<td>
											<div id="encryptToggle" class="btn-group btn-toggle" > 
												<c:choose>
													<c:when test="${symbol_dollar}{setting.encrypted}">
														<button class="btn btn-xs btn-primary active" data-id="${symbol_dollar}{setting.id}" data-encrypted="true">YES</button>
														<button class="btn btn-xs btn-default" data-id="${symbol_dollar}{setting.id}" data-encrypted="false">NO</button>
													</c:when>
													<c:otherwise>
														<button class="btn btn-xs btn-default" data-id="${symbol_dollar}{setting.id}" data-encrypted="true">YES</button>
														<button class="btn btn-xs btn-primary active" data-id="${symbol_dollar}{setting.id}" data-encrypted="false">NO</button>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>