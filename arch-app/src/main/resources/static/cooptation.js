import { cvService } from './app.js'
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
        '<form id="app" method="post" novalidate="true">\n' +
        '<div class="form-group">\n' +
        '<label>Name :</label>\n' +
        '    <input v-model="user.nom" class="form-control" />\n' +
        '<div v-if="(errors?.nom)" class="alert alert-warning">\n' +
        '        {{errors?.nom}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>firstname :</label>\n' +
        '    <input v-model="user.firstname" class="form-control"/>' +
        '    <div v-if="(errors?.firstname)" class="alert alert-warning">\n' +
        '        {{errors.firstname}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>email :</label>\n' +
        '    <input v-model="user.email" class="form-control"/>\n' +
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
        '    <input v-model="user.password" class="form-control"/>\n' +
        '    <div v-if="(errors?.password)" class="alert alert-warning">\n' +
        '        {{errors.password}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <label>birthDay :</label>\n' +
        '    <input v-model="user.birthDay" class="form-control"/>\n' +
        '    <div v-if="(errors?.birthDay)" class="alert alert-warning">\n' +
        '        {{errors.birthDay}}\n' +
        '    </div>\n' +
        '</div>\n' +
        '<div class="form-group">\n' +
        '    <button v-on:click.prevent="createUser()" class="btn btn-primary">\n' +
        '        Save</button>\n' +
        '    <button class="btn btn-primary">\n' +
        '        Abort</button>\n' +
        '</div>\n' +
        '</form>\n' +
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
                console.log(error)
                console.log(error.response.data)
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