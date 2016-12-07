<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<%--<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>--%>

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1><c:out value="${periodical.name}"/></h1>
        <h3><fmt:message key="title.top" bundle="${langPeriodical}"/></h3>

        <form id="periodicalInfo" class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-3 control-label">
                    <fmt:message key="name.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <p class="form-control-static"><c:out value="${periodical.name}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">
                    <fmt:message key="category.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <p class="form-control-static"><c:out value="${periodical.category}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">
                    <fmt:message key="publisher.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <p class="form-control-static"><c:out value="${periodical.publisher}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">
                    <fmt:message key="description.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <p class="form-control-static"><c:out value="${periodical.description}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">
                    <fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <p class="form-control-static"><c:out value="${periodical.oneMonthCost}"/></p>
                </div>
            </div>

            <auth:if-authorized mustHaveRoles="subscriber">
                <c:if test="${periodical.status == 'VISIBLE'}">
                    <div class="col-md-12 text-center">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#subscriptionModal">
                            <fmt:message key="subscribeBtn.label" bundle="${langPeriodical}"/>
                        </button>
                    </div>
                </c:if>

            </auth:if-authorized>

        </form>

        <!-- Modal window -->
        <auth:if-authorized mustHaveRoles="subscriber">
            <div class="modal fade" id="subscriptionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form method="post" action="/adminPanel/users/${thisUser.id}/invoices">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">
                                    <fmt:message key="subscriptionModal.title" bundle="${langPeriodical}"/>
                                    <label class="periodicalNameModal">"<c:out value="${periodical.name}"/>"</label>
                                </h4>
                            </div>
                            <div class="row modal-body">
                                <div class="col-md-8">
                                    <fmt:message key="subscriptionModal.bodyText" bundle="${langPeriodical}"/>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <div class="input-group date" id="subscriptionPeriod">
                                            <input name="subscriptionPeriod" class="form-control"
                                                   type="number" min="1" max="12" value="1">
                                    <div class="input-group-addon">
                                        <fmt:message key="subscriptionModal.month.label" bundle="${langPeriodical}"/>
                                    </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                    <fmt:message key="subscriptionModal.closeBtn.label" bundle="${langPeriodical}"/>
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message key="subscriptionModal.subscribeBtn.label" bundle="${langPeriodical}"/>
                                </button>
                                <input name="periodicalId" type="text" class="hidden"
                                       value="<c:out value="${periodical.id}"/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </auth:if-authorized>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>