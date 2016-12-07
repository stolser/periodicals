<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.user" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.invoice" var="langInvoice"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="user.yourAccount.title" bundle="${langPeriodical}"/></h1>
        <div class="col-md-8 col-md-offset-2">
            <p><fmt:message key="user.id.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.id}"/></p>
            <p><fmt:message key="user.username.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.userName}"/></p>
            <p><fmt:message key="user.firstName.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.firstName}"/></p>
            <p><fmt:message key="user.lastName.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.lastName}"/></p>
            <p><fmt:message key="user.email.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.email}"/></p>
            <p><fmt:message key="user.address.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.address}"/></p>
            <p><fmt:message key="user.birthday.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.birthday}"/></p>
            <p><fmt:message key="user.status.label" bundle="${langPeriodical}"/>:
                <c:out value="${thisUser.status}"/></p>
            <p><fmt:message key="user.roles.label" bundle="${langPeriodical}"/>:
            <ul>
                <c:forEach items="${thisUser.roles}" var="role">
                    <li><c:out value="${role}"/></li>
                </c:forEach>
            </ul>

            <p><a href="<c:url value="/Logout"/>">
                <fmt:message key="signout.label" bundle="${general}"/></a></p>


        </div>
    </div>

    <c:if test="${not empty userInvoices}">
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
    </c:if>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>