import { cv } from './cv.js'
import {modifyCv} from './cv.js'


axios = axios.create({
  baseURL: 'http://localhost:8081/api/',
  headers: { 'Content-Type': 'application/json' },
});

class CvService {
  getAllCvsPangined(pageNo, pageSize) {
    return axios.get('/users', { params: { pageNo: pageNo, pageSize: pageSize } })
  }

  getCvByUserId(id) {
    return axios.get('/users/' + id)
  }

  postLogin(email, password) {
    return axios.post('/users/signin?email=' + email + '&password=' + password)
  }
}

class ActivityService {
  updateActivity(activity){
    return axios.put('/activities', activity);
  }
}


export const cvService = new CvService();
export const activityService = new ActivityService();

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

const Login = {
  template: '<div>\n' +
    '<form method="post">\n' +
    '<div class="form-group">\n' +
    '<label>Email</label>\n' +
    '<input  v-model="email"/>\n' +
    '</div>\n' +
    '<div class="form-group">\n' +
    '<label>Mot de passe : </label>\n' +
    '<input v-model="password"/>\n' +
    '</div>\n' +
    '<div class="form-group">\n' +
    '<button type="submit" v-on:click.prevent="login()">Se connecter</button>\n' +
    '</div>\n' +
    '</form>\n' +
    '</div>',
  data() {
    return {
      password: '',
      email: '',
    }
  },
  methods: {
    login() {
      cvService.postLogin(this.email, this.password).then(
        (response) => {
          localStorage.setItem('jwt', response.data)

        }).catch((error) => {
          alert(error.data);
        })
    },
  },
  created() {
  }
}

const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
  { path: '/login', component: Login },
  { path:'/cv/modify/:id', component: modifyCv},
]

const router = new VueRouter({
  routes
})

const app = new Vue({
  router
}).$mount('#app')
