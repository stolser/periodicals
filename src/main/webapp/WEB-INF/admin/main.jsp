<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="main.topmessage" bundle="${general}"/></h1>
        <p><fmt:message key="mainpage.greeting" bundle="${general}"/>
            <c:out value="${thisUser.firstName}"/>
            <c:out value="${thisUser.lastName}"/>
            (<c:out value="${thisUser.userName}"/>)!</p>
    </div>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
