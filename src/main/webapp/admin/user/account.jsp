<%@include file="../../pages/includes/general.jsp" %>
<%@include file="../../pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.subscriber" var="subscriber"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<%
    request.setAttribute("user",
            org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));
%>
<div class="row">
    <h1><fmt:message key="subscriber.yourAccount.title" bundle="${subscriber}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p><fmt:message key="subscriber.username.label" bundle="${subscriber}"/>:
            <c:out value="${user.username}"/></p>
        <p><fmt:message key="subscriber.firstName.label" bundle="${subscriber}"/>:
            <c:out value="${user.givenName}"/></p>
        <p><fmt:message key="subscriber.lastName.label" bundle="${subscriber}"/>:
            <c:out value="${user.surname}"/></p>
        <p><fmt:message key="subscriber.email.label" bundle="${subscriber}"/>:
            <c:out value="${user.email}"/></p>
        <p><fmt:message key="subscriber.roles.label" bundle="${subscriber}"/>:</p>
        <ul>
            <shiro:hasRole name="admin">
                <li>Admin</li>
            </shiro:hasRole>
            <shiro:hasRole name="subscriber">
                <li>Subscriber</li>
            </shiro:hasRole>
        </ul>
        <p><a href="<c:url value="/logout"/>">
            <fmt:message key="signout.label" bundle="${general}"/></a></p>


    </div>

</div>

<%@include file="../../pages/includes/footer.jsp" %>