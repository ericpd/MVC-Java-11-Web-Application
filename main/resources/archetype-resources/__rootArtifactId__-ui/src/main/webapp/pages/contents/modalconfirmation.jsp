#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<div class="modal fade" id="modalConfirmation" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header text-center">
				<button type="button" class="close cls-modal" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title" id="modalTitle"></h3>
			</div>
			<div class="modal-body">
				<h4  id="modalMessage">
				
				</h4>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-warning cls-modal" data-dismiss="modal">No</button>
				<button id="btnYes" type="button" class="btn btn-primary">Yes</button>
			</div>
		</div>
	</div>
</div>