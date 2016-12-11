<%--<%@include file="../../includes/general.jsp" %>--%>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<%--<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>--%>

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1><c:out value="${periodical.name}"/></h1>
        <h3><fmt:message key="title.top" bundle="${langPeriodical}"/></h3>

        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="name.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.name}"/></p>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="category.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.category}"/></p>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="publisher.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.publisher}"/></p>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="description.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.description}"/></p>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.oneMonthCost}"/></p>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-3 control-label">
                <fmt:message key="status.label" bundle="${langPeriodical}"/></label>
            <div class="col-sm-9">
                <p class="form-control-static"><c:out value="${periodical.status}"/></p>
            </div>
        </div>

        <div class="row text-center">
            <custom:if-authorized mustHaveRoles="subscriber">
                <c:if test="${periodical.status == 'VISIBLE'}">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#subscriptionModal">
                        <fmt:message key="subscribeBtn.label" bundle="${langPeriodical}"/>
                    </button>
                </c:if>
            </custom:if-authorized>

            <custom:if-authorized mustHaveRoles="admin">
                <a href="<c:url
                value="${'/backend/periodicals/'.concat(periodical.id).concat('/update/')}"/>"
                   class="btn btn-warning"
                   role="button">
                    <fmt:message key="editBtn.label" bundle="${langPeriodical}"/></a>
            </custom:if-authorized>
        </div>

        <custom:if-authorized mustHaveRoles="subscriber">
            <!-- Modal window -->
            <div class="modal fade" id="subscriptionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form method="post" action="/backend/users/${thisUser.id}/invoices">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">
                                    <fmt:message key="subscriptionModal.title" bundle="${langPeriodical}"/>
                                    <label class="periodicalNameModal">"<c:out value="${periodical.name}"/>"</label>
                                </h4>
                            </div>
                            <div class="row modal-body">
                                <div class="col-xs-8">
                                    <fmt:message key="subscriptionModal.bodyText" bundle="${langPeriodical}"/>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <div class="input-group date" id="subscriptionPeriod">
                                            <input name="subscriptionPeriod" class="form-control"
                                                   type="number" min="1" max="12" value="1">
                                            <div class="input-group-addon">
                                                <fmt:message key="subscriptionModal.month.label"
                                                             bundle="${langPeriodical}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message key="subscriptionModal.subscribeBtn.label" bundle="${langPeriodical}"/>
                                </button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                    <fmt:message key="cancelBtn.label" bundle="${general}"/>
                                </button>
                                <input name="periodicalId" type="text" class="hidden"
                                       value="<c:out value="${periodical.id}"/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </custom:if-authorized>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>