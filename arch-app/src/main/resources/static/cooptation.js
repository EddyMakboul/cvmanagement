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
    template: '<div> <p> test </p> </div>',
    data() {
        return {
            user: Object.create(userModel),
        }
    },
    methods: {

    },
    created() {
        var jwt = localStorage.getItem('jwt')
        if (jwt != null) {
            cvService.isConnected(jwt).then((response) => {
                if (!response.data) {
                    this.$router.push('/')
                }
                //this.initUser();
                console.log(this.user.nom);
            })
        }
        this.$router.push('/')
    }
}