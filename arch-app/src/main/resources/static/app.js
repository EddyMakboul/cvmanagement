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
  template: '',
  data() {

  },
  created() {

  }
}

const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
]

const router = new VueRouter({
  routes
})

const app = new Vue({
  router
}).$mount('#app')
