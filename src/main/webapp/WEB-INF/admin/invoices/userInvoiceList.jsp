<%@ page import="com.stolser.javatraining.webproject.controller.ApplicationResources" %>
<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.invoice" var="langInvoice"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="userInvoiceList.title.top" bundle="${langInvoice}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="id.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="periodicalName.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="period.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="oneMonthCost.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="totalSum.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="creationDate.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="paymentDate.label" bundle="${langInvoice}"/></th>
                <th><fmt:message key="status.label" bundle="${langInvoice}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userInvoices}" var="invoice" varStatus="rowStatus">
                <tr
                        <c:if test="${invoice.status == 'PAID'}">class="success"</c:if>
                        <c:if test="${invoice.status == 'NEW'}">class="danger"</c:if>>
                    <td><c:out value="${invoice.id}"/></td>
                    <td><a href="'/adminPanel/periodicals/'<c:out value="${invoice.periodical.id}"/>">
                            <c:out value="${invoice.periodical.name}"/></a></td>
                    <td><c:out value="${invoice.subscriptionPeriod}"/></td>
                    <td><c:out value="${invoice.periodical.oneMonthCost}"/></td>
                    <td><c:out value="${invoice.totalSum}"/></td>
                    <td><fmt:formatDate value="${invoice.creationDate}" type="both"/></td>
                    <td><fmt:formatDate value="${invoice.paymentDate}" type="both"/></td>
                    <td><c:out value="${invoice.status}"/><br/>
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

</div>

<%@include file="../../includes/footer.jsp" %>