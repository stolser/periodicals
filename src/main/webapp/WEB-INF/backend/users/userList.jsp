<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.user" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.backend.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="user.usersList.title" bundle="${langUser}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="user.id.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.username.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.firstName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.lastName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.email.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.address.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.birthday.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.status.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.roles.label" bundle="${langUser}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allUsers}" var="user" varStatus="rowStatus">
                <tr class="${user.status == 'ACTIVE' ? 'success' : 'danger'}">
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.userName}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><fmt:formatDate type="date" value="${user.birthday}"/></td>
                    <td><fmt:message key="${user.status}" bundle="${langUser}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${user.roles}" var="role" varStatus="rowCount">
                                <li><fmt:message key="${role}" bundle="${general}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>

            </c:forEach>
            </tbody>

        </table>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>