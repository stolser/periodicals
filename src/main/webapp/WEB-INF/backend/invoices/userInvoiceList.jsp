<fmt:setBundle basename="webproject.i18n.backend.periodical" var="langPeriodical"/>

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
                <td>${loop.index + 1}</td>
                <td><c:choose>
                    <c:when test="${(invoice.periodical.status == 'ACTIVE') ||
                                currentUser.hasRole('admin')}">
                        <a href="/backend/periodicals/${invoice.periodical.id}">${invoice.periodical.name}</a>
                    </c:when>
                    <c:otherwise>${invoice.periodical.name}</c:otherwise>
                </c:choose></td>
                <td>${invoice.subscriptionPeriod}</td>
                <td>
                    <c:choose>
                        <c:when test="${invoice.periodical.oneMonthCost > 0}">
                            <label class="costAndSumValue">${invoice.periodical.oneMonthCost}</label>
                            <fmt:message key="standardUnit.label" bundle="${langPeriodical}"/>
                        </c:when>
                        <c:otherwise><label class="priceFreeLabel">
                            <fmt:message key="priceFree.label" bundle="${langGeneral}"/></label>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${invoice.totalSum > 0}">
                            <label class="costAndSumValue">${invoice.totalSum}</label>
                            <fmt:message key="standardUnit.label" bundle="${langPeriodical}"/>
                        </c:when>
                        <c:otherwise>
                            <label class="priceFreeLabel">
                                <fmt:message key="priceFree.label" bundle="${langGeneral}"/></label>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><custom:format-datetime value="${invoice.creationDate}"/></td>
                <td><custom:format-datetime value="${invoice.paymentDate}"/></td>
                <td class="text-center"><fmt:message key="${invoice.status}" bundle="${langInvoice}"/>
                    <c:if test="${invoice.status == 'NEW'}">
                        <form method="post"
                              action="/backend/users/${currentUser.id}/invoices/${invoice.id}/pay">
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