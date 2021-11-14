<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
	<h1>Liste des films</h1>

	<c:url var="movieAction" value="/movie" />

	<table class="table">
		<tr>
			<th>Nom</th>
			<th>Ann�e</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="movie" items="${movies}">
			<tr>
				<td>${movie.name}</td>
				<td>${movie.year}</td>
				<td><a class="btn btn-primary btn-sm"
					href="${movieAction}/${movie.id}">Montrer</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
<div>
	<a class="btn btn-primary btn-sm"
					href="/oldMovies">Montrer vieux films</a>
</div>

<div>
	<a class="btn btn-primary btn-sm"
					href="/create-movie">ajouter un film</a>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
