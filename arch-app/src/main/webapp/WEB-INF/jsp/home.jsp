<%@ include file="/WEB-INF/jsp/header.jsp" %>

    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

    <c:url var="app" value="/app.js" />


    <div id="app">
        <div>
            <nav class="navbar navbar-expand-sm navbar-dark bg-dark justify-content-between">

            <a class="navbar-brand">
                <router-link to="/">Acceuil</router-link>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <ul class="navbar-nav mr-auto">

                    <li class="nav-item" v-if="(!connected)">
                        <a class="nav-link">
                            <router-link to="/login">login</router-link>
                        </a>
                    </li>

                    <li class="nav-item" v-if="(connected)">
                        <a class="nav-link">
                            <router-link to="/cooptation">Cooptation</router-link>
                        </a>
                    </li>

                    <li class="nav-item" v-if="(connected)">
                        <a class="nav-link">
                            <button type="button" class="btn btn-link" v-on:click="logout()">Logout</button>
                        </a>
                    </li>

                </ul>

            </div>
        </nav>

        <router-view :key="$route.fullPath" @update-nav="updateNav"></router-view>
        </div>'
    </div>
    <script type="module" src="${app}"></script>
    <script type="module" src="cv.js"></script>
    <script type="module" src="login.js"></script>
    <script type="module" src="cooptation.js"></script>