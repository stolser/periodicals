<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="user.usersList.title" bundle="${langPeriodical}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="user.id.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.username.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.firstName.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.lastName.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.email.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.address.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.status.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="user.roles.label" bundle="${langPeriodical}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allUsers}" var="periodical" varStatus="rowStatus">
                <tr <c:if test="${periodical.status == 'BLOCKED'}">class="danger"</c:if>>
                    <td><c:out value="${periodical.id}"/></td>
                    <td><c:out value="${periodical.userName}"/></td>
                    <td><c:out value="${periodical.firstName}"/></td>
                    <td><c:out value="${periodical.lastName}"/></td>
                    <td><c:out value="${periodical.email}"/></td>
                    <td><c:out value="${periodical.address}"/></td>
                    <td><c:out value="${periodical.status}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${periodical.roles}" var="role" varStatus="rowCount">
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