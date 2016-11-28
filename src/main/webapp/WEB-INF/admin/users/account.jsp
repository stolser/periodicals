<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="user"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<%
    request.setAttribute("account",
            org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));
%>
<div class="row">
    <h1><fmt:message key="user.yourAccount.title" bundle="${user}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p><fmt:message key="user.username.label" bundle="${user}"/>:
            <c:out value="${account.username}"/></p>
        <p><fmt:message key="user.firstName.label" bundle="${user}"/>:
            <c:out value="${account.givenName}"/></p>
        <p><fmt:message key="user.lastName.label" bundle="${user}"/>:
            <c:out value="${account.surname}"/></p>
        <p><fmt:message key="user.email.label" bundle="${user}"/>:
            <c:out value="${account.email}"/></p>
        <p><fmt:message key="user.roles.label" bundle="${user}"/>:</p>
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

<%@include file="/WEB-INF/includes/footer.jsp" %>