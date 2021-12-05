import { cvService } from './CvService.js'
import { activityService } from './ActivityService.js'

export const cv = {
    template: '<div class="container-fluid px-1 py-5 mx-auto">\n' +
        '    <div class="row d-flex justify-content-center">\n' +
        '        <div class="col-xl-7 col-lg-8 col-md-9 col-11 text-center">\n' +
        '            <h3>Profile</h3>\n' +
        '            <div class="card">\n' +
        '                <form class="form-card">\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">First name</label>\n' +
        '                            <input type="text" id="fname" name="fname" v-model="cv.firstname" readonly >\n' +
        '                        </div>\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">Lastname</label>\n' +
        '                            <input type="text" id="lname" name="lnale" v-model="cv.nom" readonly >\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">Email</label>\n' +
        '                            <input type="text" id="email" name="email" v-model="cv.email" readonly >\n' +
        '                        </div>\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">WebSite</label>\n' +
        '                            <input type="text" id="mob" name="mob" v-model="cv.webSite" readonly >\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">BirthDay</label>\n' +
        '                            <input type="date" id="job" name="job" v-model="cv.birthDay" readonly >\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-12 flex-column d-flex">\n' +
        '                            <table class="table table-striped">\n' +
        '                                <thead>\n' +
        '                                    <tr>\n' +
        '                                        <th>Title</th>\n' +
        '                                        <th>Nature</th>\n' +
        '                                        <th>Description</th>\n' +
        '                                        <th>Year</th>\n' +
        '                                        <th>Website</th>\n' +
        '                                    </tr>\n' +
        '                                </thead>\n' +
        '                                <tbody>\n' +
        '                                    <tr v-for="activity in cv?.activities">\n' +
        '                                        <td> {{activity?.title }}</td>\n' +
        '                                        <td> {{activity?.nature}}</td>\n' +
        '                                        <td> {{activity?.description}}</td>\n' +
        '                                        <td> {{activity?.year}}</td>\n' +
        '                                        <td> {{activity?.webSite}}</td>\n' +
        '                                        <div v-if="goodUser">\n' +
        '                                            <td><button class="btn btn-danger" v-on:click.prevent="deleteActivity(activity.idActivity)">Supprimer</button>\n' +
        '                                            </td>\n' +
        '                                        </div>\n' +
        '                                    </tr>\n' +
        '                                </tbody>\n' +
        '                            </table>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </form>\n' +
        '                <div v-if="goodUser">\n' +
        '                    <button class="btn btn-primary"> <router-link  class="link-a" :to="\'/cv/\'+ idUser + \'/add\'">Ajouter une activité</router-link> </button>\n' +
        '                    <button class="btn btn-primary"> <router-link class="link-a" :to="\'/cv/\'+ idUser + \'/modify\'">Modifier</router-link> </button>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>',
    data() {
        return {
            cv: null,
            idUser: -1,
            goodUser: false,
        }

    },
    methods: {
        deleteActivity(idActivity) {
            var resultat = confirm("Voulez-vous vraiment supprimer cette activité ?");
            if (resultat == true) {
                var jwt = localStorage.getItem('jwt')
                activityService.removeActivity(idActivity, jwt).then((response) => {
                    cvService.getCvByUserId(this.idUser).then((response) => {
                        this.cv = response.data;
                        this.idUser = this.$route.params.id;
                    })
                })
            }
        }
    },
    created() {
        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            this.cv = response.data;
            this.cv.birthDay = this.cv.birthDay.slice(0, 10)
            this.idUser = this.$route.params.id;
        })

        var jwt = localStorage.getItem('jwt')

        if (jwt != null) {
            cvService.isGoodUser(this.$route.params.id, jwt).then(
                (response) => {
                    this.goodUser = response.data;
                }
            ).catch(
                (error) => {
                    this.goodUser = false;
                }
            )
        }
    }
}

export const addActivity = {
    template: '<div>\n' +
        '    <h1 class="d-flex justify-content-center text-dark">Add Activity</h1>\n' +
        '    <div class="border border-secondary rounded p-3">\n' +
        '        <form class="form-center edit-person" id="app" method="post" novalidate="true">\n' +
        '            <div class="form-group">\n' +
        '                <label>title :</label>\n' +
        '                <input type="text" v-model="activity.title" class="form-control" placeholder="experience title" />\n' +
        '                <div v-if="(errors?.title)" class="alert alert-warning">\n' +
        '                    {{errors?.title}}\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="form-group">\n' +
        '                <label>nature :</label>\n' +
        '                <input type="text" v-model="activity.nature" class="form-control" placeholder="experience nature" />\n' +
        '                <div v-if="(errors?.nature)" class="alert alert-warning">\n' +
        '                    {{errors.nature}}\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="form-group">\n' +
        '                <label>description :</label>\n' +
        '                <input type="text" v-model="activity.description" class="form-control"\n' +
        '                    placeholder="experience description" />\n' +
        '                <div v-if="(errors?.description)" class="alert alert-warning">\n' +
        '                    {{errors.description}}\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="form-group">\n' +
        '                <label>year :</label>\n' +
        '                <input type="number" v-model="activity.year" class="form-control" placeholder="year of experience" />\n' +
        '                <div v-if="(errors?.year)" class="alert alert-warning">\n' +
        '                    {{errors.year}}\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="form-group">\n' +
        '                <label>webSite :</label>\n' +
        '                <input type="webSite" v-model="activity.webSite" class="form-control" placeholder="link to project" />\n' +
        '                <div v-if="(errors?.webSite)" class="alert alert-warning">\n' +
        '                    {{errors.webSite}}\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="form-validator">\n' +
        '                <button type="submit" v-on:click.prevent="goCvPage()" class="btn btn-primary">Save</button>\n' +
        '                <button class="btn btn-danger" type="submit" v-on:click.prevent="cancel()"> Abort</button>\n' +
        '            </div>\n' +
        '        </form>\n' +
        '    </div>\n' +
        '</div>',
    data() {
        return {
            idUser: null,
            activity: {},
            user: null,
            errors: [],
        }
    },
    methods: {
        goCvPage() {
            this.activity.user = this.user;
            var jwt = localStorage.getItem('jwt')
            activityService.addActivity(this.activity, jwt).then((response) => {
                this.$router.push('/cv/' + this.idUser);
            }).catch((error) => {
                this.errors = error.response.data;
            })

        },
        cancel() {
            this.$router.push('/cv/' + this.idUser);
        }
    },
    created() {

        var jwt = localStorage.getItem('jwt')
        if (jwt != null) {
            cvService.isConnected(jwt).then((response) => {
                if (!response.data) {
                    this.$router.push('/')
                }
                this.jwt = jwt
            })
        }
        else {
            this.$router.push('/')
        }

        if (jwt != null) {
            cvService.isGoodUser(this.$route.params.id, jwt).then(
                (response) => {
                    if (!response.data) {
                        this.$router.push('/')
                    }
                    this.goodUser = response.data;
                }
            ).catch(
                (error) => {
                    this.$router.push('/')
                }
            )
        }

        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            this.idUser = this.$route.params.id;
            this.user = response.data;
        })
    },
}

export const modifyCv = {
    template: '<div class="container-fluid px-1 py-5 mx-auto">\n' +
        '    <div class="row d-flex justify-content-center">\n' +
        '        <div class=" text-center">\n' +
        '            <h3>Update Profile</h3>\n' +
        '            <div class="card">\n' +
        '                <form class="form-card">\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">First name</label>\n' +
        '                            <input type="text" id="fname" name="fname" v-model="cv.firstname">\n' +
        '                              <div v-if="(errors?.firstname)" class="alert alert-warning">\n' +
        '                                   {{errors?.firstname}}\n' +
        '                              </div>\n' +
        '                        </div>\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">Lastname</label>\n' +
        '                            <input type="text" id="lname" name="lname" v-model="cv.nom">\n' +
        '                              <div v-if="(errors?.nom)" class="alert alert-warning">\n' +
        '                                   {{errors?.nom}}\n' +
        '                              </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">Email</label>\n' +
        '                            <input type="email" id="email" name="email" v-model="cv.email">\n' +
        '                              <div v-if="(errors?.email)" class="alert alert-warning">\n' +
        '                                   {{errors?.email}}\n' +
        '                              </div>\n' +
        '                        </div>\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">WebSite</label>\n' +
        '                            <input type="text" id="web" name="web" placeholder="Enter your website" v-model="cv.webSite">\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-sm-6 flex-column d-flex">\n' +
        '                            <label class="form-control-label px-3">BirthDay</label>\n' +
        '                            <input type="date" id="date" name="date" v-model="cv.birthDay">\n' +
        '                              <div v-if="(errors?.birthDay)" class="alert alert-warning">\n' +
        '                                   {{errors?.birthDay}}\n' +
        '                              </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="row justify-content-between text-left">\n' +
        '                        <div class="form-group col-12 flex-column d-flex">\n' +
        '                            <table class="table table-striped">\n' +
        '                                <thead>\n' +
        '                                    <tr>\n' +
        '                                        <th>Title</th>\n' +
        '                                        <th>Nature</th>\n' +
        '                                        <th>Description</th>\n' +
        '                                        <th>Year</th>\n' +
        '                                        <th>Website</th>\n' +
        '                                    </tr>\n' +
        '                                </thead>\n' +
        '                                <tbody>\n' +
        '                                    <tr v-for="(item, index) in cv?.activities">\n' +
        '                                        <td><input type="text" v-model="cv?.activities.at(index).title" /></td>\n' +
        '                                        <td><input type="text" v-model="cv?.activities.at(index).nature" /></td>\n' +
        '                                        <td><input type="text" v-model="cv?.activities.at(index).description" /></td>\n' +
        '                                        <td><input type="text" v-model="cv?.activities.at(index).year" /></td>\n' +
        '                                        <td><input type="text" v-model="cv?.activities.at(index).webSite" /></td>\n' +
        '                                    </tr>\n' +
        '                                </tbody>\n' +
        '                            </table>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <button class="btn btn-primary" type="submit" v-on:click.prevent="goCvPage()">Save</button>\n' +
        '                    <button class="btn btn-danger" type="submit" v-on:click.prevent="cancel()">abort</button>\n ' +
        '                </form>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>',
    data() {
        return {
            idUser: null,
            cv: null,
            errors: [],
        }
    },
    methods: {
        goCvPage() {
            this.errors = null;
            var jwt = localStorage.getItem('jwt')
            for (let i = 0; i < this.cv.activities.length; i++) {
                activityService.updateActivity(this.cv.activities.at(i), jwt);
            }
            var jwt = localStorage.getItem('jwt');
            cvService.updateUser(this.cv, jwt).then((response) => {
                localStorage.setItem('jwt', response.data.jwt);
                this.$router.push('/cv/' + this.idUser);
            }).catch((error) => {
                this.errors = error.response.data;
            });

        },
        cancel() {
            this.$router.push('/cv/' + this.idUser);
        }
    },
    created() {

        var jwt = localStorage.getItem('jwt')
        if (jwt != null) {
            cvService.isConnected(jwt).then((response) => {
                if (!response.data) {
                    this.$router.push('/')
                }
                this.jwt = jwt
            })
        }
        else {
            this.$router.push('/')
        }

        if (jwt != null) {
            cvService.isGoodUser(this.$route.params.id, jwt).then(
                (response) => {
                    if (!response.data) {
                        this.$router.push('/')
                    }
                    this.goodUser = response.data;
                }
            ).catch(
                (error) => {
                    this.$router.push('/')
                }
            )
        }

        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            this.idUser = this.$route.params.id;
            this.cv = response.data;
            this.cv.birthDay = this.cv.birthDay.slice(0, 10)
        })
    },
}