<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="home" value="/app" />
<c:url var="app" value="/app.js" />

    
<div id="myApp">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${home}">Movies</a>
    <a class="navbar-brand" href="app" v-on:click="populateMovies()">Populate</a>
    <a class="navbar-brand" href="app" v-on:click="listMovies()">List of movies</a>
    <button class="navbar-brand" v-on:click="addMovie()">Add Movie</buton>
</nav>
<p> {{editable}}</p>
    <div class="container">
        <h1>My application</h1>
        
        <div v-if="(movie != null)">
        	<p>{{ movie }}</p>
		</div>
		
		<div v-if="(editable != null)">
		
		<form id="app" method="post" novalidate="true">

		    <div class="form-group">
		        <label>Name :</label>
		        <input v-model="editable.name" class="form-control" 
		            v-bind:class="{'is-invalid':errors.name}" />
		        <div v-if="(errors.name)" class="alert alert-warning">
		            {{errors.name}}
		        </div>
		    </div>
		    <div class="form-group">
		        <label>Year :</label>
		        <input v-model="editable.year" class="form-control"
		            v-bind:class="{'is-invalid':errors.year}" number />
		        <div v-if="(errors.year)" class="alert alert-warning">
		            {{errors.year}}
		        </div>
		    </div>
		    <div class="form-group">
		        <label>Description :</label>
		        <textarea v-model="editable.description" rows="5" cols="50"
		            class="form-control"></textarea>
		    </div>
		    <div class="form-group">
		        <button v-on:click.prevent="submitMovie()" class="btn btn-primary">
		            Save</button>
		        <button v-on:click="listMovies()" class="btn btn-primary">
		            Abort</button>
		    </div>
			</form>
		</div>
				
		
        <table class="table">
		<tr>
			<th>Nom</th>
			<th>Ann�e</th>
			<th>Actions</th>
		</tr>
			<tr v-for="movie in movies">
				<td>{{movie.name}}</td>
				<td>{{movie.year}}</td>
				<td><button v-on:click="getMovie(movie)">Montrer</button>
				<button v-on:click="deleteMovie(movie.id)">Supprimer</button>
				<button v-on:click="editMovie(movie)">Editer</button>
				</td>
			</tr>
	</table>
    </div>
</div>
</div>
<script src="${app}"></script>

<div id="home">
    <div class="container">	
        <table class="table">
		<tr>
			<th>Nom Prenom</th>
			<th>Actions</th>
		</tr>
			<tr v-for="movie in cvs">
				<td>{{movie.nom}} {{movie.firstname}} </td>
				<td> <button v-on:click="showCv()">Show Cv</button></td>
			</tr>
		</table>
		</div>

	<button v-on:click="previousPage()">previous page</button>
	<button v-on:click="nextpage()">next page</button>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>