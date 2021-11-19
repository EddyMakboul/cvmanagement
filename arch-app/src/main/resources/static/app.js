import { cv } from './cv.js'
import { Login } from './login.js'

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

  isConnected(jwt) {
    return axios.get('/users/isconnected', { headers: { Authorization: 'Bearer ' + jwt } })
  }

  logout(jwt) {
    return axios.post('/users/logout', { headers: { Authorization: 'Bearer ' + jwt } })
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

    app.updateNav();
  }
}



const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
  { path: '/login', component: Login },
]

export const router = new VueRouter({
  routes
})

const app = Vue.extend({
  template:
    '<div>\n' +
    ' <nav class="navbar navbar-light bg-light justify-content-between">\n' +
    '   <div v-if="(!connected)">\n' +
    '     <a class="navbar-brand">\n' +
    '       <router-link to="/login">login</router-link>\n' +
    '     </a>\n' +
    '   </div>\n' +
    '   <div v-if="(connected)">\n' +
    '     <a class="navbar-brand">\n' +
    '       <button v-on:click="logout()">Logout</button>\n' +
    '     </a>\n' +
    '   </div>\n' +
    ' </nav>\n' +
    ' <router-view :key="$route.fullPath"></router-view>\n' +
    '</div>',
  data() {
    return {
      connected: false
    }
  },
  methods: {
    updateNav() {
      var jwt = localStorage.getItem('jwt')
      if (jwt != null) {
        cvService.isConnected(jwt).then((response) => {
          this.connected = response.data;
        })
      }
    },
    logout() {
      var jwt = localStorage.getItem('jwt')
      if (jwt != null) {
        cvService.logout(jwt).then((response) => {
          connected = false;
          localStorage.removeItem('jwt')
        })
      }
    }
  },
  created() {
    this.updateNav();
  }
})
new app({
  router
}).$mount('#app')
