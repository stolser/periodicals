<div class="modal fade" id="subscriptionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form method="post" action="/backend/users/${currentUser.id}/invoices">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">
                        <fmt:message key="subscriptionModal.title" bundle="${langPeriodical}"/>
                        <label class="periodicalNameModal">"${periodical.name}"</label>
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
                    <input name="periodicalId" type="text" class="hidden" value="${periodical.id}"/>
                </div>
            </form>
        </div>
    </div>
</div>