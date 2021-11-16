<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

<c:url var="app" value="/app.js" />


<div id="app">
  <router-view></router-view>
</div>
<script type="module" src="${app}"></script>
<script type="module" src="cv.js"></script>
