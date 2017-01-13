<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.frontend.general" var="frontGeneral"/>

<div class="row">
    <h1><fmt:message key="home.topMessage" bundle="${frontGeneral}"/></h1>
    <c:choose>
        <c:when test="${sessionScope[ApplicationResources.CURRENT_USER_ATTR_NAME] != null}">
            <p><fmt:message key="visitYour.text" bundle="${frontGeneral}"/>
                <a href="<c:url value="${ApplicationResources.CURRENT_USER_ACCOUNT_URI}"/>">
                    <fmt:message key="accountPage.text" bundle="${frontGeneral}"/>
                </a>.
            </p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="mainpage.unauthorized.user.description" bundle="${frontGeneral}"/>.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>

