import { cvService } from './app.js'

export const Login = {
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
                    this.$emit('update-nav')
                    this.$router.push('/')

                }).catch((error) => {
                    alert(error.response.data);
                })
        },
    },
    created() {
    }
}