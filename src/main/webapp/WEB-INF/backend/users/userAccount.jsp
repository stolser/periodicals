<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webproject.i18n.backend.general" var="general"/>
<fmt:setBundle basename="webproject.i18n.backend.user" var="langUser"/>
<fmt:setBundle basename="webproject.i18n.backend.subscription" var="langSubscription"/>
<fmt:setBundle basename="webproject.i18n.backend.invoice" var="langInvoice"/>

<div class="row">
    <h1 class="col-md-12"><fmt:message key="user.yourAccount.title" bundle="${langUser}"/></h1>

    <div class="userAccountTabs col-md-12">
        <ul class="nav nav-tabs" role="tablist">
            <c:if test="${not empty userSubscriptions}">
                <li role="presentation">
                    <a href="#subscriptions" aria-controls="subscriptions" role="tab" data-toggle="tab">
                        <fmt:message key="userSubscriptionList.title.top" bundle="${langSubscription}"/>
                    </a>
                </li>
            </c:if>

            <c:if test="${not empty userInvoices}">
                <li role="presentation">
                    <a href="#invoices" aria-controls="invoices" role="tab" data-toggle="tab">
                        <fmt:message key="userInvoiceList.title.top" bundle="${langInvoice}"/>
                    </a></li>
            </c:if>

            <li role="presentation" class="active">
                <a href="#personalInfo" aria-controls="personalInfo" role="tab" data-toggle="tab">
                    <fmt:message key="user.yourPersonalInfo.title" bundle="${langUser}"/></a>
            </li>

        </ul>

        <div class="tab-content">
            <c:if test="${not empty userSubscriptions}">
                <div role="tabpanel" class="tab-pane" id="subscriptions">
                    <%@include file="/WEB-INF/backend/subscriptions/userSubscriptionList.jsp" %>
                </div>
            </c:if>

            <c:if test="${not empty userInvoices}">
                <div role="tabpanel" class="tab-pane" id="invoices">
                    <%@include file="/WEB-INF/backend/invoices/userInvoiceList.jsp" %>
                </div>
            </c:if>

            <div role="tabpanel" class="tab-pane active" id="personalInfo">
                <%@include file="/WEB-INF/backend/users/userPersonalInfo.jsp" %>
            </div>
        </div>

    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>