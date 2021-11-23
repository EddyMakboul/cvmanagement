import { cv } from './cv.js'
import { modifyCv } from './cv.js'

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

  searchCvByCriteria(criteria, pageNo, pageSize) {
    return axios.get('/users/search', { params: { criteria: criteria, pageNo: pageNo, pageSize: pageSize } })
  }

  size(criteria) {
    return axios.get('/users/size', { params: { criteria: criteria } })
  }

  updateUser(userdto, jwt){
    return axios.put('/users', userdto, { headers: { Authorization: 'Bearer ' + jwt } });
  }
}

class ActivityService {
  updateActivity(activity) {
    return axios.put('/activities', activity);
  }

  removeActivity(idActivity){
    // alert(idActivity);
    return axios.delete('/activities', {data:{idActivity}});
  }
}


export const cvService = new CvService();
export const activityService = new ActivityService();

const allCv = {
  template:

    '<div id="home">\n' +
    '<form class="form-inline">\n' +
    '        <input v-model="cvFilter" id="cvFilter" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">\n' +
    '        <button  v-on:click.prevent="searchCvs()" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>\n' +
    '</form>\n' +
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
      cvFilter: '',
      isFiltered: false,
      pageMax: 0,
    }
  },
  methods: {
    getNextPage() {
      if (this.page < this.pageMax - 1) {
        this.page += 1;
        if (this.isFiltered) {
          cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
            this.cvs = response.data;
          })
        } else {
          cvService.getAllCvsPangined(this.page, this.size).then((response) => {
            this.cvs = response.data;
          });
        }
      }


    },
    getPreviousPage() {
      if (this.page > 0) {
        this.page -= 1;
        if (this.isFiltered) {
          cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
            this.cvs = response.data;
          })
        } else {
          cvService.getAllCvsPangined(this.page, this.size).then((response) => {
            this.cvs = response.data;
          });
        }

      }

    },
    searchCvs() {
      this.page = 0;
      this.isFiltered = true;
      cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
        this.cvs = response.data;
      })
      this.getSize();

    },

    getSize() {
      cvService.size(this.cvFilter).then((response) => {
        this.pageMax = Math.round(response.data / this.size)
        if (this.pageMax < response.data / this.size) {
          this.pageMax += 1;
        }
        console.log(this.pageMax)
      })
    }

  },
  created() {
    cvService.getAllCvsPangined(this.page, this.size).then((response) => {
      this.cvs = response.data;
    })

    this.getSize();

  }
}



const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
  { path: '/login', component: Login },
  { path: '/cv/:id/modify', component: modifyCv },
]

const app = Vue.extend({
  template:
    '<div>\n' +
    ' <nav class="navbar navbar-light bg-light justify-content-between">\n' +
    '   <div v-if="(!connected)">\n' +
    '     <a class="navbar-brand">\n' +
    '       <router-link to="/login">login</router-link>\n' +
    '     </a>\n' +
    '   </div>\n' +
    '   <div v-else-if="(connected)">\n' +
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
          this.connected = false;
          localStorage.removeItem('jwt')
        })
      }
    }
  },
  created() {
    this.updateNav();
  }
})

const router = new VueRouter({
  routes,
})

new app({
  router
}).$mount('#app')
