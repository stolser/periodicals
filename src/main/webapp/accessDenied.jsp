<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.general" var="general"/>

<div class="row">
    <h1><fmt:message key="accessDenied" bundle="${general}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <p>
            <fmt:message key="accessDenied.text" bundle="${general}">
                <fmt:param value="${sessionScope.thisUser.firstName}"/>
                <fmt:param value="${sessionScope.thisUser.lastName}"/>
            </fmt:message>
        </p>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>