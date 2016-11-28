<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="main.topmessage" bundle="${general}"/></h1>
    <p>Hi <c:out value="${thisUser.firstName}"/> <c:out value="${thisUser.lastName}"/>
        (<c:out value="${thisUser.userName}"/>)!</p>

    <p><a href="<c:url value="/login.jsp"/>">Log in</a></p>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
