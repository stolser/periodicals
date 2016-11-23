<%@include file="../../pages/includes/general.jsp" %>
<%@include file="../../pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.subscriber" var="subscriber"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<%
    request.setAttribute("account",
            org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));
%>
<div class="row">
    <h1><fmt:message key="subscriber.yourAccount.title" bundle="${subscriber}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p>Can be accessed only by Admins</p>


    </div>

</div>

<%@include file="../../pages/includes/footer.jsp" %>