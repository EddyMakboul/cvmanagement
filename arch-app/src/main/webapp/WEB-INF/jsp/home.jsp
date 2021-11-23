<%@ include file="/WEB-INF/jsp/header.jsp" %>

    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

    <c:url var="app" value="/app.js" />


    <div id="app">
        <div>
            <nav class="navbar navbar-light bg-light justify-content-between">
                <div v-if="(!connected)">
                    <a class="navbar-brand">
                        <router-link to="/login">login</router-link>
                    </a>
                </div>
                <div v-else-if="(connected)">
                    <a class="navbar-brand">
                        <router-link to="/cooptation">Cooptation</button>
                    </a>
                    <a class="navbar-brand">
                        <button v-on:click="logout()">Logout</button>
                    </a>
                </div>
                <div class="acceuil-bouton">
                    <a class="navbar-brand">
                        <router-link to="/">Acceuil</router-link>
                    </a>
                </div>
            </nav>
            <router-view :key="$route.fullPath" @update-nav="updateNav"></router-view>
        </div>'
    </div>
    <script type="module" src="${app}"></script>
    <script type="module" src="cv.js"></script>
    <script type="module" src="login.js"></script>