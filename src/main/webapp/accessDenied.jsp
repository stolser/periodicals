<%@include file="pages/includes/general.jsp" %>
<%@include file="pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="accessDenied" bundle="${general}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p>Sorry, ${sessionScope.username}, access denied!</p>


    </div>

</div>

<%@include file="pages/includes/footer.jsp" %>