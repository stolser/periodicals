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
            <tr
                    <c:if test="${subscription.status == 'ACTIVE'}">class="success"</c:if>
                    <c:if test="${subscription.status == 'INACTIVE'}">class="danger"</c:if>>
                <td><c:out value="${subscription.id}"/></td>
                <td><a href="/adminPanel/periodicals/<c:out value="${subscription.periodical.id}"/>">
                    <c:out value="${subscription.periodical.name}"/></a></td>
                <td><c:out value="${subscription.deliveryAddress}"/></td>
                <td><custom:format-datetime value="${subscription.endDate}"/></td>
                <td><c:out value="${subscription.status}"/></td>
            </tr>

        </c:forEach>
        </tbody>

    </table>
</div>