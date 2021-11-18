import { cv } from './cv.js'

const API_URL = 'http://localhost:8081/api';

class CvService {
  getAllCvsPangined(pageNo, pageSize) {
    return axios.get(API_URL + '/users', { params: { pageNo: pageNo, pageSize: pageSize } })
  }

  getCvByUserId(id) {
    return axios.get(API_URL + '/users/' + id)
  }
}


export const cvService = new CvService();

const allCv = {
  template: '<div id="home">\n' +
    '<div class="container">	\n' +
    '<table class="table">\n' +
    '<tr>\n' +
    '<th>Nom Prenom</th>\n' +
    '<th>Actions</th>\n' +
    '</tr>\n' +
    '<tr v-for="cv in cvs">\n' +
    '<td>{{cv.nom}} {{cv.firstname}} </td>\n' +
    '<td> <router-link :to="\'/cv/\'+ cv.idUser">show</router-link></td>\n' +
    '</tr>\n' +
    '</table>\n' +
    '</div>\n' +

    '<button v-on:click="getPreviousPage()">previous page</button>\n' +
    '<button v-on:click="getNextPage()">next page</button>\n' +
    '</div>',
  data() {
    return {
      cvs: [],
      page: 0,
      size: 15,
    }
  },
  methods: {
    getNextPage() {
      this.page += 1;
      cvService.getAllCvsPangined(this.page, this.size).then((response) => {
        this.cvs = response.data;
      });

    },
    getPreviousPage() {
      if (this.page > 0) {
        this.page -= 1;
        cvService.getAllCvsPangined(this.page, this.size).then((response) => {
          this.cvs = response.data;
        });
      }

    },
    searchPersosn() {

    }

  },
  created() {
    cvService.getAllCvsPangined(this.page, this.size).then((response) => {
      this.cvs = response.data;
    })
  }
}

const login = {
  template: '<div>\n' +
  '<form method="post">\n' +
      '<div class="emailGroup">\n' +
          '<label for="mail">Email</label>\n' +
          '<input type="email" name="email" id="email" v-model="email"/>\n' +
      '</div>\n' +
      '<div class="pswGroup">\n' +
          '<label for="password">Mot de passe : </label>\n' +
          '<input type="password" name="password" id="password" v-model="password"/>\n' +
      '</div>\n' +
      '<div class="submit">\n' +
          '<button type="submit" v-on:click="login()">Se connecter</button>\n' +
      '</div>\n'+
  '</form>\n' +
'</div>',
  data() {
    return{
      email: null,
      password: null,
    }
  },
  methods: {
    login(){
        var t = null;
        // alert(this.mail);
        axios.post(API_URL + '/users/signin',
        this.email, 
          this.password)
          .then((response)=>{alert(t);});
        // alert(t);
    }
  }
}

const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
  { path:'/login', component: login},
]

const router = new VueRouter({
  routes
})

const app = new Vue({
  router
}).$mount('#app')
