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
            <p><fmt:message key="user.status.label" bundle="${langUser}"/>:
                <c:out value="${thisUser.status}"/></p>
            <p><fmt:message key="user.roles.label" bundle="${langUser}"/>:
            <ul>
                <c:forEach items="${thisUser.roles}" var="role">
                    <li><c:out value="${role}"/></li>
                </c:forEach>
            </ul>

            <p><a href="<c:url value="/Logout"/>">
                <fmt:message key="signout.label" bundle="${general}"/></a></p>


        </div>
    </div>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>