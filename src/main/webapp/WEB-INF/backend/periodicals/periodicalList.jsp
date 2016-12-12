<%--<%@include file="../../includes/general.jsp" %>--%>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="periodicalList.title.top" bundle="${langPeriodical}"/></h1>
        <table class="table table-hover table-bordered table-striped text-center">
            <thead>
            <tr>
                <th><fmt:message key="id.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="name.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="category.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="publisher.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="status.label" bundle="${langPeriodical}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allPeriodicals}" var="periodical" varStatus="rowStatus">
                <c:if test="${(periodical.status == 'VISIBLE') || thisUser.hasRole('admin')}">
                    <tr class="${periodical.status == 'VISIBLE' ? 'success' :
                    (periodical.status == 'INVISIBLE' ? 'warning' : 'danger')}">
                        <td><c:out value="${periodical.id}"/></td>
                        <td>
                            <a href="/backend/periodicals/${periodical.id}">
                                <c:out value="${periodical.name}"/></a></td>
                        <td><c:out value="${periodical.category}"/></td>
                        <td><c:out value="${periodical.publisher}"/></td>
                        <td><c:out value="${periodical.oneMonthCost}"/></td>
                        <td><fmt:message key="${periodical.status}" bundle="${langPeriodical}"/></td>
                    </tr>
                </c:if>

            </c:forEach>
            </tbody>

        </table>

    </div>

    <div class="col-md-12">
        <custom:if-authorized mustHaveRoles="admin">
            <div class="row">
                <div class="col-sm-6 col-xs-6 text-left">
                    <a href="<% out.print(ApplicationResources.PERIODICAL_CREATE_NEW_HREF); %>"
                       class="btn btn-primary active" role="button">
                        <fmt:message key="newPeriodicalBt.label" bundle="${langPeriodical}"/>
                    </a>
                </div>
                <div class="col-sm-6 col-xs-6 text-right">
                    <a href="<% out.print(ApplicationResources.PERIODICAL_DELETE_DISCARDED); %>"
                       class="btn btn-danger active" role="button">
                        <fmt:message key="deleteDiscardedBt.label" bundle="${langPeriodical}"/>
                    </a>
                </div>
            </div>
        </custom:if-authorized>
        <%--<custom:if-authorized mustHaveRoles="*" mustNotHaveRoles="admin">--%>
            <%--<p><fmt:message key="youCannotUpdatePeriodicals.text" bundle="${langPeriodical}"/></p>--%>
        <%--</custom:if-authorized>--%>
    </div>

</div>

<%@include file="../../includes/footer.jsp" %>