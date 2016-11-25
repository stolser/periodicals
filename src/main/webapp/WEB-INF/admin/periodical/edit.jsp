<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="user"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="user.yourAccount.title" bundle="${user}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p>Can be accessed only by Admins</p>


    </div>

</div>

<%@include file="../../includes/footer.jsp" %>