<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.frontend.general" var="frontGeneral"/>

<div class="row">
    <h1><fmt:message key="home.topMessage" bundle="${frontGeneral}"/></h1>
    <% if (session.getAttribute("thisUser") != null) {%>
    <p><fmt:message key="visitYour.text" bundle="${frontGeneral}"/>
        <a href="<c:url value="/backend/users/currentUser"/>">
            <fmt:message key="accountPage.text" bundle="${frontGeneral}"/>
        </a>.</p>
    <%} else {%>
    <p><fmt:message key="mainpage.unauthorized.user.description" bundle="${frontGeneral}"/>.</p>
    <%}%>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>

