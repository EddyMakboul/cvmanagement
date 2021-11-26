import { cvService } from './app.js'

export const Login = {
    template:
        '<section class="ftco-section">\n' +
        '    <div class="container">\n' +
        '        <div class="row justify-content-center">\n' +
        '            <div class="col-md-6 text-center mb-5">\n' +
        '                <h2 class="heading-section">Login</h2>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="row justify-content-center">\n' +
        '            <div class="col-md-7 col-lg-5">\n' +
        '                <div class="login-wrap p-4 p-md-5">\n' +
        '                    <div class="icon d-flex align-items-center justify-content-center">\n' +
        '                        <i class="bi bi-person"></i>\n' +
        '                    </div>\n' +
        '                    <h3 class="text-center mb-4">Sign In</h3>\n' +
        '                    <form action="#" class="login-form">\n' +
        '                        <div class="form-group">\n' +
        '                            <input type="email" class="form-control rounded-left" placeholder="Email" required=""\n' +
        '                                v-model="email">\n' +
        '                        </div>\n' +
        '                        <div class="form-group d-flex">\n' +
        '                            <input type="password" class="form-control rounded-left" placeholder="Password" required=""\n' +
        '                                v-model="password">\n' +
        '                        </div>\n' +
        '                        <div class="form-group">\n' +
        '                            <button type="submit" v-on:click.prevent="login()"\n' +
        '                                class="form-control btn btn-primary rounded submit px-3">Login</button>\n' +
        '                        </div>\n' +
        '                    </form>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</section>',
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