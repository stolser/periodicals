<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="user.usersList.title" bundle="${langUser}"/></h1>
    <div class="col-md-12 table-responsive">
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="user.id.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.username.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.firstName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.lastName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.email.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.address.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.status.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.roles.label" bundle="${langUser}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allUsers}" var="user" varStatus="rowStatus">
                <tr <c:if test="${user.status == 'BLOCKED'}">class="danger"</c:if>>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.userName}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.address}"/></td>
                    <td><c:out value="${user.status}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${user.roles}" var="role" varStatus="rowCount">
                                <li><c:out value="${role}"/></li>
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