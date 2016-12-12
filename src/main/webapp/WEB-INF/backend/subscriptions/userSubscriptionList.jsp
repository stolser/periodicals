<fmt:setBundle basename="webProject.i18n.admin.subscription" var="langSubscription"/>

<div id="userInvoiceList" class="col-md-12 table-responsive">
    <h1><fmt:message key="userSubscriptionList.title.top" bundle="${langSubscription}"/></h1>
    <table class="table table-hover table-bordered table-striped text-center">
        <thead>
        <tr class="text-center">
            <th><fmt:message key="id.label" bundle="${langSubscription}"/></th>
            <th><fmt:message key="periodicalName.label" bundle="${langSubscription}"/></th>
            <th><fmt:message key="deliveryAddress.label" bundle="${langSubscription}"/></th>
            <th><fmt:message key="endDate.label" bundle="${langSubscription}"/></th>
            <th><fmt:message key="status.label" bundle="${langSubscription}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userSubscriptions}" var="subscription" varStatus="rowStatus">
            <tr class="${subscription.status == 'ACTIVE' ? 'success' : 'danger'}">
                <td><c:out value="${subscription.id}"/></td>
                <td><c:choose>
                    <c:when test="${(subscription.periodical.status == 'VISIBLE') ||
                                thisUser.hasRole('admin')}">
                        <a href="/backend/periodicals/${subscription.periodical.id}">
                            <c:out value="${subscription.periodical.name}"/></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${subscription.periodical.name}"/>
                    </c:otherwise>
                </c:choose></td>
                <td><c:out value="${subscription.deliveryAddress}"/></td>
                <td><custom:format-datetime value="${subscription.endDate}"/></td>
                <td><fmt:message key="${subscription.status}" bundle="${langSubscription}"/></td>
            </tr>

        </c:forEach>
        </tbody>

    </table>
</div>