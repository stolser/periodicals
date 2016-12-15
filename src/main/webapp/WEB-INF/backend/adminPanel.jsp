<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="adminPanel.topmessage" bundle="${general}"/></h1>
        <p><fmt:message key="adminPanel.greeting" bundle="${general}"/>
            <c:out value="${thisUser.firstName}"/>
            <c:out value="${thisUser.lastName}"/>
            (<c:out value="${thisUser.userName}"/>)!
            <fmt:message key="adminPanel.topdescription" bundle="${general}"/></p>
    </div>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
