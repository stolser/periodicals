<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webproject.i18n.backend.general" var="general"/>
<fmt:setBundle basename="webproject.i18n.backend.adminPanel" var="langAdminPanel"/>
<fmt:setBundle basename="webproject.i18n.backend.periodical" var="langPeriodical"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="adminPanel.topmessage" bundle="${general}"/></h1>
        <p><fmt:message key="adminPanel.topdescription" bundle="${general}"/></p>

        <div class="panel-group" id="statistics" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="periodicalHeading">
                    <a role="button" class="panel-title btn btn-default" data-toggle="collapse"
                       data-parent="#statistics" href="#periodicalCollapse"
                       aria-expanded="true" aria-controls="periodicalCollapse">
                        <fmt:message key="stat.periodical.title" bundle="${langAdminPanel}"/>
                    </a>
                </div>
                <div id="periodicalCollapse" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="periodicalHeading">
                    <div class="panel-body">
                        <table class="table table-hover table-bordered table-striped text-center">
                            <thead>
                            <tr>
                                <th><fmt:message key="stat.periodical.categoryLabel" bundle="${langAdminPanel}"/></th>
                                <th><fmt:message key="stat.periodical.activeLabel" bundle="${langAdminPanel}"/></th>
                                <th><fmt:message key="stat.periodical.inactiveLabel" bundle="${langAdminPanel}"/></th>
                                <th><fmt:message key="stat.periodical.discardedLabel" bundle="${langAdminPanel}"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${periodicalStatistics}" var="statItem" varStatus="loop">
                                <tr>
                                    <td><fmt:message key="${statItem.category.messageKey}"
                                                     bundle="${langPeriodical}"/></td>
                                    <td>${statItem.active}</td>
                                    <td>${statItem.inActive}</td>
                                    <td>${statItem.discarded}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="invoiceHeading">
                    <a class="panel-title btn btn-default"  role="button" data-toggle="collapse"
                       data-parent="#statistics" href="#invoiceCollapse"
                       aria-expanded="true" aria-controls="invoiceCollapse">
                        <fmt:message key="stat.invoice.title" bundle="${langAdminPanel}"/>
                    </a>
                </div>
                <div id="invoiceCollapse" class="panel-collapse collapse" role="tabpanel"
                     aria-labelledby="invoiceHeading">
                    <div class="panel-body">
                        <h3><fmt:message key="stat.invoice.description" bundle="${langAdminPanel}"/></h3>
                        <p><fmt:message key="totalInvoiceSum.text" bundle="${langAdminPanel}"/>:
                            <label class="costAndSumValue">${financialStatistics.totalInvoiceSum}</label>
                            <fmt:message key="standardUnit.label" bundle="${langPeriodical}"/>
                        </p>
                        <p><fmt:message key="paidInvoiceSum.text" bundle="${langAdminPanel}"/>:
                            <label class="costAndSumValue">${financialStatistics.paidInvoiceSum}</label>
                            <fmt:message key="standardUnit.label" bundle="${langPeriodical}"/>
                        </p>
                    </div>
                </div>
            </div>

        </div>

    </div>


</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
