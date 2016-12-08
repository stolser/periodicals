<fmt:setBundle basename="webProject.i18n.admin.invoice" var="langInvoice"/>

<div id="userInvoiceList" class="col-md-12 table-responsive">
    <h1><fmt:message key="userInvoiceList.title.top" bundle="${langInvoice}"/></h1>
    <table class="table table-hover table-bordered table-striped text-center">
        <thead>
        <tr class="text-center">
            <th><fmt:message key="id.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="periodicalName.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="period.label" bundle="${langInvoice}"/><br/>
                <label class="theadSublabel">
                    (<fmt:message key="period.inmonth.label" bundle="${langInvoice}"/>)
                </label>
            </th>
            <th><fmt:message key="oneMonthCost.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="totalSum.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="creationDate.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="paymentDate.label" bundle="${langInvoice}"/></th>
            <th><fmt:message key="status.label" bundle="${langInvoice}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userInvoices}" var="invoice" varStatus="rowStatus">
            <tr class="${invoice.status == 'PAID' ? 'success' : 'danger'}">
                <td><c:out value="${invoice.id}"/></td>
                <td><a href="/adminPanel/periodicals/<c:out value="${invoice.periodical.id}"/>">
                    <c:out value="${invoice.periodical.name}"/></a></td>
                <td><c:out value="${invoice.subscriptionPeriod}"/></td>
                <td><c:out value="${invoice.periodical.oneMonthCost > 0.0 ? invoice.periodical.oneMonthCost : 'free'}"/></td>
                <td><c:out value="${invoice.totalSum > 0.0 ? invoice.totalSum : 'free'}"/></td>
                <td><custom:format-datetime value="${invoice.creationDate}"/></td>
                <td><custom:format-datetime value="${invoice.paymentDate}"/></td>
                <td class="text-center"><fmt:message key="${invoice.status}" bundle="${langInvoice}"/>
                    <c:if test="${invoice.status == 'NEW'}">
                        <form method="post"
                              action="/adminPanel/users/${thisUser.id}/invoices/${invoice.id}/pay">
                            <button type="submit" class="btn btn-primary btn-block">
                                <fmt:message key="payInvoiceBtn.label" bundle="${langInvoice}"/>
                            </button>
                        </form>
                    </c:if>
                </td>

            </tr>

        </c:forEach>
        </tbody>

    </table>
</div>