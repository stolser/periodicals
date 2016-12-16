<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>
<fmt:setBundle basename="webProject.i18n.admin.adminPanel" var="langAdminPanel"/>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>

<div class="row">
    <div class="col-md-12">
        <h1><fmt:message key="adminPanel.topmessage" bundle="${general}"/></h1>
        <p><fmt:message key="adminPanel.greeting" bundle="${general}"/>
            <c:out value="${thisUser.firstName}"/>
            <c:out value="${thisUser.lastName}"/>
            (<c:out value="${thisUser.userName}"/>)!
            <fmt:message key="adminPanel.topdescription" bundle="${general}"/></p>

        <%--PERIODICAL_STATISTICS_ATTR_NAME = <c:out value="${ApplicationResources.PERIODICAL_STATISTICS_ATTR_NAME}"/>--%>

        <div class="panel-group" id="statistics" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="periodicalHeading">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#statistics" href="#periodicalCollapse"
                           aria-expanded="true" aria-controls="periodicalCollapse">
                            <fmt:message key="stat.periodical.title" bundle="${langAdminPanel}"/>
                        </a>
                    </h4>
                </div>
                <div id="periodicalCollapse" class="panel-collapse collapse" role="tabpanel"
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
                                    <td><c:out value="${statItem.active}"/></td>
                                    <td><c:out value="${statItem.inActive}"/></td>
                                    <td><c:out value="${statItem.discarded}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="invoiceHeading">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#statistics" href="#invoiceCollapse"
                           aria-expanded="true" aria-controls="invoiceCollapse">
                            <fmt:message key="stat.invoice.title" bundle="${langAdminPanel}"/>
                        </a>
                    </h4>
                </div>
                <div id="invoiceCollapse" class="panel-collapse collapse" role="tabpanel"
                     aria-labelledby="invoiceHeading">
                    <div class="panel-body">
                    </div>
                </div>
            </div>

            <%--<div class="panel panel-default">--%>
            <%--<div class="panel-heading" role="tab" id="invoiceHeading">--%>
            <%--<h4 class="panel-title">--%>
            <%--<a role="button" data-toggle="collapse" data-parent="#statistics" href="#invoiceCollapse"--%>
            <%--aria-expanded="true" aria-controls="invoiceCollapse">--%>
            <%--<fmt:message key="stat.invoice.title" bundle="${langAdminPanel}"/>--%>
            <%--</a>--%>
            <%--</h4>--%>
            <%--</div>--%>
            <%--<div id="invoiceCollapse" class="panel-collapse collapse in" role="tabpanel"--%>
            <%--aria-labelledby="invoiceHeading">--%>
            <%--<div class="panel-body">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>

    </div>



</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
