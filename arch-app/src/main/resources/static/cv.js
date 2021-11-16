import { cvService } from './app.js'

export const cv = {
    template: '<table class = "table table-striped">\n' +
        '      <thead>\n' +
        '      <tr>\n' +
        '        <th>Id</th>\n' +
        '        <th>First Name</th>\n' +
        '        <th>Last Name</th>\n' +
        '        <th>Email</th>\n' +
        '      </tr>\n' +
        '\n' +
        '      </thead>\n' +
        '      <tbody>\n' +
        '      <tr v-for="cv in cv?.activities">\n' +
        '        <td> {{cv?.title }}</td>\n' +
        '        <td> {{cv?.description}}</td>\n' +
        '        <td> {{cv?.year}}</td>\n' +
        '        <td> {{cv?.webSite}}</td>\n' +
        '      </tr>\n' +
        '      </tbody>\n' +
        '    </table>',
    data() {
        return {
            cv: null,
        }

    },
    methods: {

    },
    created() {
        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            this.cv = response.data;
        })
    }
}