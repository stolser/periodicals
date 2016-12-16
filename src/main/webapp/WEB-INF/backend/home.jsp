<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>

<div class="row">
    <h1><fmt:message key="home.topMessage" bundle="${langGeneral}"/></h1>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>

