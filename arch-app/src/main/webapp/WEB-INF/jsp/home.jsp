<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

<c:url var="app" value="/app.js" />


<div id="app">
  
</div>
<script type="module" src="${app}"></script>
<script type="module" src="cv.js"></script>
<script type="module" src="login.js"></script>
