<%@ page import="com.stolser.javatraining.webproject.controller.ApplicationResources" %>
<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="periodicalList.title.top" bundle="${langUser}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="id.label" bundle="${langUser}"/></th>
                <th><fmt:message key="name.label" bundle="${langUser}"/></th>
                <th><fmt:message key="category.label" bundle="${langUser}"/></th>
                <th><fmt:message key="publisher.label" bundle="${langUser}"/></th>
                <th><fmt:message key="oneMonthCost.label" bundle="${langUser}"/></th>
                <th><fmt:message key="status.label" bundle="${langUser}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allPeriodicals}" var="user" varStatus="rowStatus">

                <tr
                        <c:if test="${user.status == 'VISIBLE'}">class="success"</c:if>
                        <c:if test="${user.status == 'INVISIBLE'}">class="warning"</c:if>
                        <c:if test="${user.status == 'DISCARDED'}">class="danger"</c:if>>
                    <td><c:out value="${user.id}"/></td>
                    <td>
                        <a href="<% out.print(ApplicationResources.PERIODICAL_LIST_HREF); %><c:out value="${user.id}"/>">
                            <c:out value="${user.name}"/></a></td>
                    <td><c:out value="${user.category}"/></td>
                    <td><c:out value="${user.publisher}"/></td>
                    <td><c:out value="${user.oneMonthCost}"/></td>
                    <td><c:out value="${user.status}"/></td>
                </tr>

            </c:forEach>
            </tbody>

        </table>

    </div>

    <div class="col-md-12">
        <auth:if-authorized mustHaveRoles="admin">
            <a href="<% out.print(ApplicationResources.PERIODICAL_CREATE_NEW_HREF); %>"
               class="btn btn-primary active" role="button">
                <fmt:message key="newPeriodicalBt.label" bundle="${langUser}"/>
            </a>
        </auth:if-authorized>
        <auth:if-authorized mustHaveRoles="*" mustNotHaveRoles="admin">
            <p>You are NOT an admin, so you <b>cannot</b> create new periodicals!!!</p>
        </auth:if-authorized>
    </div>

</div>

<%@include file="../../includes/footer.jsp" %>