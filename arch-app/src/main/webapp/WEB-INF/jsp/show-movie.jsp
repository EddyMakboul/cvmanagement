<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
	<h1>D�tails d'un film</h1>

	<table class="table">
		<c:if test= "${sessionScope.isAdmin}">
			<tr>
				<th>Id :</th>
				<td>${movie.id}</td>
			</tr>
		</c:if>
		<tr>
			<th>Nom :</th>
			<td>${movie.name}</td>
		</tr>
		<tr>
			<th>Ann�e :</th>
			<td>${movie.year}</td>
		</tr>
		<tr>
			<th>Description :</th>
			<td>${movie.description}</td>
			<td></td>
		</tr>
	</table>

	<c:url var="editAction" value="/edit-movie" />
	<c:url var="deleteAction" value="/delete-movie" />
	<c:url var="moviesAction" value="/movies" />

	<p>
		<a class="btn btn-primary btn-sm" href="${editAction}/${movie.id}">Modifier</a>
		<a class="btn btn-danger btn-sm" href="${deleteAction}/${movie.id}">Supprimer</a>
		<a class="btn btn-primary btn-sm" href="${moviesAction}">Liste des films</a>
	</p>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
