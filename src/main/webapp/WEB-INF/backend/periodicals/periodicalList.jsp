<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="periodicalList.title.top" bundle="${langPeriodical}"/></h1>
        <table class="table table-hover table-bordered table-striped text-center">
            <thead>
            <tr>
                <th><fmt:message key="number.label" bundle="${general}"/></th>
                <th><fmt:message key="name.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="category.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="publisher.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></th>
                <custom:if-authorized mustHaveRoles="admin">
                    <th><fmt:message key="status.label" bundle="${langPeriodical}"/></th>
                </custom:if-authorized>
            </tr>
            </thead>

            <tbody>
            <c:set var="onlyVisibleIndex" value="0"/>
            <c:forEach items="${allPeriodicals}" var="periodical" varStatus="loop">
                <c:if test="${(periodical.status == 'ACTIVE') || thisUser.hasRole('admin')}">
                    <c:set var="onlyVisibleIndex" value="${onlyVisibleIndex + 1}"/>
                    <tr class="${periodical.status == 'ACTIVE' ? 'success' :
                    (periodical.status == 'INACTIVE' ? 'warning' : 'danger')}">
                        <td><c:out value="${onlyVisibleIndex}"/></td>
                        <td>
                            <a href="/backend/periodicals/${periodical.id}">
                                <c:out value="${periodical.name}"/></a></td>
                        <td><fmt:message key="${periodical.category.messageKey}"
                                         bundle="${langPeriodical}"/></td>
                        <td><c:out value="${periodical.publisher}"/></td>
                        <td><c:out value="${periodical.oneMonthCost > 0.0
                        ? periodical.oneMonthCost : 'free'}"/></td>
                        <custom:if-authorized mustHaveRoles="admin">
                            <td><fmt:message key="${periodical.status}" bundle="${langPeriodical}"/></td>
                        </custom:if-authorized>
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
                    <a href="<% out.print(ApplicationResources.PERIODICAL_CREATE_NEW_URI); %>"
                       class="btn btn-primary" role="button">
                        <fmt:message key="newPeriodicalBt.label" bundle="${langPeriodical}"/>
                    </a>
                </div>
                <div class="col-sm-6 col-xs-6 text-right">
                    <form method="post" action="<% out.print(ApplicationResources.PERIODICAL_DELETE_DISCARDED_URI); %>">
                        <button type="submit" class="btn btn-danger">
                            <fmt:message key="deleteDiscardedBt.label" bundle="${langPeriodical}"/>
                        </button>
                    </form>
                </div>
            </div>
        </custom:if-authorized>
    </div>

</div>

<%@include file="../../includes/footer.jsp" %>