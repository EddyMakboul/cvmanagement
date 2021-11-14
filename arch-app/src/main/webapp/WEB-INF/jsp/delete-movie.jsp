<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="deleteAction" value="/delete-movie" />
<c:url var="movieAction" value="/movie" />

<div class="container">

	<form:form method="delete" modelAttribute="movie">
	
		<p> Etes vous sur de vouloir supprimer le film ${movie.name} ?<p>
		
		<button type="submit" class="btn btn-primary">Oui</button>
		<a class="btn btn-primary btn-sm"
						href="${movieAction}/${movie.id}">Non</a>
	</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
