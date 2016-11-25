<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="user.usersList.title" bundle="${langUser}"/></h1>
    <div class="col-md-8 col-md-offset-2">
        <c:forEach items="${allUsers}" var="user">
            <p><fmt:message key="user.id.label" bundle="${langUser}"/>:
                <c:out value="${user.id}"/></p>
            <p><fmt:message key="user.username.label" bundle="${langUser}"/>:
                <c:out value="${user.userName}"/></p>
            <p><fmt:message key="user.firstName.label" bundle="${langUser}"/>:
                <c:out value="${user.firstName}"/></p>
            <p><fmt:message key="user.lastName.label" bundle="${langUser}"/>:
                <c:out value="${user.lastName}"/></p>
            <p><fmt:message key="user.email.label" bundle="${langUser}"/>:
                <c:out value="${user.email}"/></p>
            <p><fmt:message key="user.address.label" bundle="${langUser}"/>:
                <c:out value="${user.address}"/></p>
            <p><fmt:message key="user.roles.label" bundle="${langUser}"/>:
                <c:out value="${user.roles}"/></p>
            <ul>
                <c:forEach items="${user.roles}" var="role">
                    <li><c:out value="${role}"/></li>
                </c:forEach>
            </ul>

        </c:forEach>


    </div>

</div>

<%@include file="../../includes/footer.jsp" %>