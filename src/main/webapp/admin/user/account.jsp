<%@include file="../../pages/includes/general.jsp" %>
<%@include file="../../pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="user"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<%
    request.setAttribute("account",
            org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));
%>
<div class="row">
    <h1><fmt:message key="subscriber.yourAccount.title" bundle="${user}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p><fmt:message key="subscriber.username.label" bundle="${user}"/>:
            <c:out value="${account.username}"/></p>
        <p><fmt:message key="subscriber.firstName.label" bundle="${user}"/>:
            <c:out value="${account.givenName}"/></p>
        <p><fmt:message key="subscriber.lastName.label" bundle="${user}"/>:
            <c:out value="${account.surname}"/></p>
        <p><fmt:message key="subscriber.email.label" bundle="${user}"/>:
            <c:out value="${account.email}"/></p>
        <p><fmt:message key="subscriber.roles.label" bundle="${user}"/>:</p>
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