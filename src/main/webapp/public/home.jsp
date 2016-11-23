<%@include file="../pages/includes/general.jsp" %>
<%@include file="../pages/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.frontend.general" var="frontendGeneral"/>


<div class="row">
    <h1><fmt:message key="home.topMessage" bundle="${frontendGeneral}"/></h1>
    <shiro:authenticated>
        <p>Visit your <a href="<c:url value="/admin/user/account.jsp"/>">account page</a>.</p>
    </shiro:authenticated>
    <shiro:notAuthenticated><p>If you want to access the authenticated-only
        <a href="<c:url value="/admin/user/account.jsp"/>">account page</a>,
        you will need to log-in first.</p></shiro:notAuthenticated>
</div>

<%@include file="../pages/includes/footer.jsp" %>

