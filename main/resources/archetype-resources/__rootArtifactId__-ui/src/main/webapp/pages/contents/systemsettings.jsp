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
							<h2 class="heading">System Settings</h2>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-12 text-right">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="${symbol_pound}systemModal">
							  Add
							</button>
						</div>
					</div>
					<div class="row"><div class="col-md-12">&nbsp;</div></div>
					<div class="row">
						<div class="col-md-12 table-responsive">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>${symbol_pound}</th>
										<th>Field</th>
										<th>Value</th>
										<th>Encrypted</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${symbol_dollar}{requestScope.settings}" var="setting" >
										<tr>
											<td><a href='systemsettings/edit?id=${symbol_dollar}{setting.id}&encrypted=${symbol_dollar}{setting.encrypted}' ><c:out value="${symbol_dollar}{setting.id}" /></a></td>
											<td><c:out value="${symbol_dollar}{setting.name}" /></td>
											<td><c:out value="${symbol_dollar}{setting.value}" /></td>
											<td><c:out value="${symbol_dollar}{setting.encrypted}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="systemModal" tabindex="-1" role="dialog" aria-labelledby="systemModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
	    <form id="systemForm" method="post" data-action="systemsettings">
	      <div class="modal-header">
	        <h3 id="modalTitle">Add item</h3>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <input name="name" type="text" class="form-control" placeholder="name" required/><br />
	        <input name="value" type="text" class="form-control" placeholder="value" required/><br />
	        <label>Encrypted:</label>&nbsp;<input type="checkbox" name="encrypted"/><br />
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
	        <button type="submit" class="btn btn-primary">Save</button>
	      </div>
      </form>
    </div>
  </div>
</div>