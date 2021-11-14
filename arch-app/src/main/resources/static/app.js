const movie = {
	name:null,
	year:null,
	description:null,
};

const myApp = {

    // Préparation des données
    data() {
        console.log("data");
        return {
            counter: 1,
            message: "Hello",
            list: [10, 20, 30],
            axios: null,
			movies:[],
			movie:null,
			editable:null,
			errors:[],
			isNew:null,
			
        }
    },
    // Mise en place de l'application
    mounted() {
        console.log("Mounted ");
        this.axios = axios.create({
            baseURL: 'http://localhost:8081/api/',
            timeout: 1000,
            headers: { 'Content-Type': 'application/json' },
        });
		this.axios.get('/movies')
    			.then(r => {
		        console.log("get all movies");
		        this.movies = r.data;
		    });
    },

    methods: {
		refresh:function(){
			this.axios.get('/movies')
    			.then(r => {
		        console.log("get all movies");
		        this.movies = r.data;
		    });
		},
		deleteMovie: function(id){
			console.log("on supprimer un film");
			this.axios.delete('/movies/' + id)
				.then(r =>{
					this.refresh.call();
				});
		},
		getMovie:function(movie){
			this.movie = movie;
		},
		populateMovies:function(){
			this.axios.patch('/movies/')
			.then(r =>{
				this.refresh.call();
			});
		},
		editMovie:function(movie){
			this.editable = movie;
		},
		addMovie:function(){
			this.editable = Object.create(movie);
			this.isNew = true;
		},
		
		submitMovie:function(){
			if(this.editable.name == ""){
				this.errors.name="le nom ne doit pas être vide'";
			}
			else{
				this.errors.name = null;
			}
			if(this.editable.year < 1900 || this.editable.year > 2100){
				this.errors.year = "l'age doit être comprise entre 1900 et 2100";
			}
			else{
				this.errors.year = null;
			}
			if(this.editable.description.length > 200 ){
				this.errors.description= "la description doit être inferieure à 200 charactères"
			}
			else{
				this.errors.description = null;
			}
			if(this.errors.name == null && this.errors.year == null && this.errors.description == null){
				if(this.isNew == null){
					this.axios.put('/movies', this.editable)
						.then(r =>{
							this.editable = null;
							this.refresh.call();
						});
				}
				else {
					this.axios.post('/movies', this.editable)
						.then(r =>{
							this.editable = null;
							this.isNew = null;
							this.refresh.call();
						});
				}
				
			}
		},
    }
}

Vue.createApp(myApp).mount('#myApp');