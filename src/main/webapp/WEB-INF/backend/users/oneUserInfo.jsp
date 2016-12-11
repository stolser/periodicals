<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="user.yourAccount.title" bundle="${langUser}"/></h1>
        <div class="col-md-8 col-md-offset-2">
            <p><fmt:message key="user.id.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.id}"/></p>
            <p><fmt:message key="user.username.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.userName}"/></p>
            <p><fmt:message key="user.firstName.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.firstName}"/></p>
            <p><fmt:message key="user.lastName.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.lastName}"/></p>
            <p><fmt:message key="user.email.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.email}"/></p>
            <p><fmt:message key="user.address.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.address}"/></p>
            <p><fmt:message key="user.birthday.label" bundle="${langUser}"/>:
                <fmt:formatDate type="date" value="${thisUser.birthday}"/></p>
            <p><fmt:message key="user.status.label" bundle="${langUser}"/>:
                <fmt:message key="${thisUser.status}" bundle="${langUser}"/></p>
            <p><fmt:message key="user.roles.label" bundle="${langUser}"/>:
            <ul>
                <c:forEach items="${thisUser.roles}" var="role">
                    <li><fmt:message key="${role}" bundle="${general}"/></li>
                </c:forEach>
            </ul>

            <p><a href="<c:url value="/backend/signOut"/>">
                <fmt:message key="signout.label" bundle="${general}"/></a></p>


        </div>
    </div>

    <c:if test="${not empty userInvoices}">
        <%@include file="/WEB-INF/backend/invoices/userInvoiceList.jsp" %>
    </c:if>

    <c:if test="${not empty userSubscriptions}">
        <%@include file="/WEB-INF/backend/subscriptions/userSubscriptionList.jsp" %>
    </c:if>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>