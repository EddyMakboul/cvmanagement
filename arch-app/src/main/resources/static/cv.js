import { cvService } from './app.js'
import { activityService } from './app.js'

export const cv = {
    template: '<table class = "table table-striped">\n' +
        '      <thead>\n' +
        '      <tr>\n' +
        '        <th>Title</th>\n' +
        '        <th>Nature</th>\n' +
        '        <th>Description</th>\n' +
        '        <th>Year</th>\n' +
        '        <th>Website</th>\n' +
        '      </tr>\n' +
        '\n' +
        '      </thead>\n' +
        '      <tbody>\n' +
        '      <tr v-for="cv in cv?.activities">\n' +
        '        <td> {{cv?.title }}</td>\n' +
        '        <td> {{cv?.nature}}</td>\n' +
        '        <td> {{cv?.description}}</td>\n' +
        '        <td> {{cv?.year}}</td>\n' +
        '        <td> {{cv?.webSite}}</td>\n' +
        '        <td> <router-link :to="\'/cv/modify/\'+ idUser">Modifier</router-link></td>\n' +
        '      </tr>\n' +
        '      </tbody>\n' +
        '    </table>',
    data() {
        return {
            cv: null,
            idUser: -1,
        }

    },
    methods: {

    },
    created() {
        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            this.cv = response.data;
            this.idUser = this.$route.params.id;
        })
    }
}

export const modifyCv = {
    template: '<table class="table table-striped">\n' +
    '          <thead>\n' +
    '          <tr>\n' +
    '               <th>Title</th>\n' +
    '               <th>Nature</th>\n' +
    '               <th>Description</th>\n' +
    '               <th>Year</th>\n' +
    '               <th>Website</th>\n' +
    '          </tr>\n' +
    '          </thead>\n' +
    '\n' +
    '          <tbody>\n' +
    '          <tr v-for="(item, index) in cv?.activities">\n' +
    '               <td><input type="text" v-model="cv?.activities.at(index).title" /></td>\n' +
    '               <td><input type="text" v-model="cv?.activities.at(index).nature"/></td>\n' +
    '               <td><input type="text" v-model="cv?.activities.at(index).description"/></td>\n' +
    '               <td><input type="text" v-model="cv?.activities.at(index).year"/></td>\n' +
    '               <td><input type="text" v-model="cv?.activities.at(index).webSite"/></td>' +
    '          </tr>\n' +
    '          </tbody>\n' +
    '          <button type="submit" v-on:click.prevent="goCvPage()">Valider Modification</button>' +
    '          </table>\n',
    data(){
        return {
            cv: null,
        }
    },
    methods: {
        goCvPage(){
            // alert(this.cv.activities.at(0).title);
            for(let i=0; i<this.cv.activities.length; i++){
                // const movie = {name: "movie" + i, year: 1999 + i, description: "tu connais " + i}
                activityService.updateActivity(this.cv.activities.at(i));
            }
            // this.$router.go(-1) : this.$router.push('/')
        },
    },
    created(){
        cvService.getCvByUserId(this.$route.params.id).then((response) => {
            // alert('okok');
            this.cv = response.data;
            // alert(response.data.activities.at(0).title);
        })
    },
}