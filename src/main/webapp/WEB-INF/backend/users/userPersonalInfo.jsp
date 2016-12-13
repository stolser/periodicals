<div class="col-md-12">
    <h2><fmt:message key="user.yourPersonalInfo.sub-title" bundle="${langUser}"/></h2>
    <p><fmt:message key="user.yourPersonalInfo.description" bundle="${langUser}"/></p>
    <div class="col-md-8 col-md-offset-2">
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
        <p><fmt:message key="user.roles.label" bundle="${langUser}"/>:
        <ul>
            <c:forEach items="${thisUser.roles}" var="role">
                <li><fmt:message key="${role}" bundle="${general}"/></li>
            </c:forEach>
        </ul>
    </div>
</div>