#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="${parentArtifactId}" uri="${parentArtifactId}Tag" %>

<!-- Bootstrap core JavaScript -->
<script src="assets/vendor/jquery/jquery.min.js?<${parentArtifactId}:currentDate />"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js?<${parentArtifactId}:currentDate />"></script>

<!-- Menu Toggle Script -->
<script>
	${symbol_dollar}("${symbol_pound}menu-toggle").click(function(e) {
		e.preventDefault();
		${symbol_dollar}("${symbol_pound}wrapper").toggleClass("toggled");
	});
</script>

<c:choose>
	<c:when test='${symbol_dollar}{not empty sessionScope and sessionScope.account_type == "ADMIN"}'>
		<script type="text/javascript">
			${symbol_dollar}("body").keydown(function(e){
				if(e.which === 84 && e.shiftKey && e.altKey) {
					alert("success keys...");
				}	
			});
		</script>
	</c:when>
</c:choose>