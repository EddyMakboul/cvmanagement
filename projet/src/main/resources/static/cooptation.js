import { cvService } from './CvService.js'

const userModel = {
    nom: null,
    firstname: null,
    email: null,
    webSite: null,
    birthDay: null,
    password: null,
}

export const Cooptation = {
    template:
        '<div>\n' +
        '<h1 class="d-flex justify-content-center text-dark">Cooptation</h1>\n' +
        '<div class="border border-secondary rounded p-3">\n' +
        '<form class="form-center edit-person" id="app" method="post" novalidate="true">\n' +
        '<div class="form-group">\n' +
        '<label>Name :</label>\n' +
        '    <input type="text" v-model="user.nom" class="form-control" />\n' +
        '<div v-if="(errors?.nom)" class="alert alert-warning">\n' +
        '        {{errors?.nom}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>firstname :</label>\n' +
        '    <input type="text" v-model="user.firstname" class="form-control"/>' +
        '    <div v-if="(errors?.firstname)" class="alert alert-warning">\n' +
        '        {{errors.firstname}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>email :</label>\n' +
        '    <input type="email" v-model="user.email" class="form-control"/>\n' +
        '    <div v-if="(errors?.email)" class="alert alert-warning">\n' +
        '        {{errors.email}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>webSite :</label>\n' +
        '    <input v-model="user.webSite" class="form-control"/>\n' +
        '    <div v-if="(errors?.webSite)" class="alert alert-warning">\n' +
        '        {{errors.webSite}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>password :</label>\n' +
        '    <input type="password" v-model="user.password" class="form-control"/>\n' +
        '    <div v-if="(errors?.password)" class="alert alert-warning">\n' +
        '        {{errors.password}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>birthDay :</label>\n' +
        '    <input type="date" v-model="user.birthDay" class="form-control"/>\n' +
        '    <div v-if="(errors?.birthDay)" class="alert alert-warning">\n' +
        '        {{errors.birthDay}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-validator">\n' +
        '    <button v-on:click.prevent="createUser()" class="btn btn-primary">\n' +
        '        Save</button>\n' +
        '    <button class="btn btn-danger">\n' +
        '        Abort</button>\n' +
        '</div>\n' +
        '</form>\n' +
        '</div>\n' +
        '</div>',
    data() {
        return {
            user: Object.create(userModel),
            errors: [],
            jwt: null
        }
    },
    methods: {

        createUser() {
            console.log(this.user)
            cvService.createUser(this.user, this.jwt).then((response) => {
                this.$router.push('/')
            }).catch((error) => {
                this.errors = error.response.data
            })
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
    }
}