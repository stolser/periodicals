<%@include file="../pages/includes/general.jsp" %>
<%@include file="../pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="main.topmessage" bundle="${general}"/></h1>
    <p>Hi <shiro:guest>Guest</shiro:guest>
        <shiro:user>
            <%
                //This should never be done in a normal page and should exist in a proper MVC controller of some sort, but for this
                //tutorial, we'll just pull out Stormpath Account data from Shiro's PrincipalCollection to reference in the
                //<c:out/> tag next:

                request.setAttribute("account", org.apache.shiro.SecurityUtils.getSubject().getPrincipals().oneByType(java.util.Map.class));

            %>
            <c:out value="${account.givenName}"/>
            <c:out value="${account.username}"/>,
            <c:out value="${account.email}"/></shiro:user>!
        ( <shiro:user><a href="<c:url value="/logout"/>">Log out</a></shiro:user>
        <shiro:guest><a href="<c:url value="/login.jsp"/>">Log in</a></shiro:guest> )
    </p>
</div>

<%@include file="../pages/includes/footer.jsp" %>
