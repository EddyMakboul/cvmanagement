import { cv } from './cv.js'
import { modifyCv } from './cv.js'
import { addActivity } from './cv.js'
import { cvService } from './CvService.js'

import { Login } from './login.js'
import { Cooptation } from './cooptation.js'

axios = axios.create({
  baseURL: 'http://localhost:8081/api/',
  headers: { 'Content-Type': 'application/json' },
});



class ActivityService {
  updateActivity(activity) {
    return axios.put('/activities', activity);
  }

  removeActivity(idActivity) {
    // alert(idActivity);
    return axios.delete('/activities', { data: { idActivity } });
  }

  addActivity(activity) {
    return axios.post('/activities', activity);
  }
}


export const activityService = new ActivityService();

const allCv = {
  template:

    '<div id="home">\n' +
    '<div class="search-form-container">\n' +
    ' <form class="form-inline">\n' +
    '         <input v-model="cvFilter" id="cvFilter" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">\n' +
    '         <button  v-on:click.prevent="searchCvs()" class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>\n' +
    ' </form>\n' +
    '</div>\n' +
    '<div class="container">	\n' +
    '<div id="card-container">\n' +
    '    <div class="row justify-content-center mt-lg-3 mt-2 gx-lg-1 px-4 px-lg-0">\n' +
    '        <div v-for="cv in cvs"\n' +
    '            class="card col-xl-3 col-md-5 col-sm-8 mx-lg-3 mx-2 my-lg-4 my-sm-3 my-2 shadow border-0 h-auto">\n' +
    '            <div class="card-body">\n' +
    '                <div class="card-title row justify-content-between d-flex align-items-center">\n' +
    '                    <h5 class="fs-5 col-auto mb-0">{{cv?.nom}} {{cv?.firstname}}</h5>\n' +
    '                </div>\n' +
    '                <h6 class="card-subtitle fs-6 mb-2 text-muted">{{cv?.email}}</h6>\n' +
    '                <div id="skills-section" class="row justify-content-start mt-lg-2">\n' +
    '                    <div v-for="activity in cv.activities" class="col-auto mx-lg-2 mx-1 my-lg-2 px-0">\n' +
    '                        <span  class="badge badge-pill badge-primary p-2"> {{activity?.nature}}\n' +
    '                        </span>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '                <router-link class="btn btn-outline-primary mt-3 col-12" :to="\'/cv/\'+ cv.idUser">Show Cv</router-link>\n' +
    '            </div>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</div>\n' +
    '</div>\n' +
    '<div class="button-container">\n' +
    ' <button type="button" class="btn btn-primary" v-on:click="getPreviousPage()">previous page</button>\n' +
    ' <button type="button" class="btn btn-primary" v-on:click="getNextPage()">next page</button>\n' +
    '</div>\n' +
    '</div>',
  data() {
    return {
      cvs: [],
      page: 0,
      size: 15,
      cvFilter: '',
      pageMax: 0,
    }
  },
  methods: {
    getNextPage() {
      if (this.page < this.pageMax - 1) {
        this.page += 1;
        cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
          this.cvs = response.data;
        })

      }


    },
    getPreviousPage() {
      if (this.page > 0) {
        this.page -= 1;
        cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
          this.cvs = response.data;
        })
      }

    },
    searchCvs() {
      this.page = 0;
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
    cvService.searchCvByCriteria(this.cvFilter, this.page, this.size).then((response) => {
      this.cvs = response.data;
    })
    var jwt = localStorage.getItem('jwt')
    if (jwt != null) {
      cvService.isConnected(jwt).then((response) => {
        if (!response.data) {
          this.localStorage.removeItem('jwt');
        }
      })
    }
    else {
      this.localStorage.removeItem('jwt');
    }

    this.getSize();

  }
}



const routes = [
  { path: '/', component: allCv },
  { path: '/cv/:id', component: cv },
  { path: '/login', component: Login },
  { path: '/cooptation', component: Cooptation },
  { path: '/cv/:id/modify', component: modifyCv },
  { path: '/cv/:id/add', component: addActivity },
]

let app = Vue.extend({
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
          this.$router.push('/')
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
