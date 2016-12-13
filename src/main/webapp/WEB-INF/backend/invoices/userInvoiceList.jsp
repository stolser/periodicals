<div id="userInvoiceList" class="col-md-12 table-responsive">
    <h2><fmt:message key="userInvoiceList.sub-title" bundle="${langInvoice}"/></h2>
    <p><fmt:message key="userInvoiceList.description.top" bundle="${langInvoice}"/></p>
    <table class="table table-hover table-bordered table-striped text-center">
        <thead>
        <tr class="text-center">
            <th><fmt:message key="number.label" bundle="${general}"/></th>
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
        <c:forEach items="${userInvoices}" var="invoice" varStatus="loop">
            <tr class="${invoice.status == 'PAID' ? 'success' : 'danger'}">
                <td><c:out value="${loop.index + 1}"/></td>
                <td><c:choose>
                    <c:when test="${(invoice.periodical.status == 'VISIBLE') ||
                                thisUser.hasRole('admin')}">
                        <a href="/backend/periodicals/${invoice.periodical.id}">
                            <c:out value="${invoice.periodical.name}"/></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${invoice.periodical.name}"/>
                    </c:otherwise>
                </c:choose></td>
                <td><c:out value="${invoice.subscriptionPeriod}"/></td>
                <td><c:out value="${invoice.periodical.oneMonthCost > 0.0 ? invoice.periodical.oneMonthCost : 'free'}"/></td>
                <td><c:out value="${invoice.totalSum > 0.0 ? invoice.totalSum : 'free'}"/></td>
                <td><custom:format-datetime value="${invoice.creationDate}"/></td>
                <td><custom:format-datetime value="${invoice.paymentDate}"/></td>
                <td class="text-center"><fmt:message key="${invoice.status}" bundle="${langInvoice}"/>
                    <c:if test="${invoice.status == 'NEW'}">
                        <form method="post"
                              action="/backend/users/${thisUser.id}/invoices/${invoice.id}/pay">
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