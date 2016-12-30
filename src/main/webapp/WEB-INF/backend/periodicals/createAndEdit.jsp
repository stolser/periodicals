<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.general" var="general"/>

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <c:if test="${periodicalOperationType == 'create'}">
            <h1><fmt:message key="createPeriodical.title" bundle="${langPeriodical}"/></h1>
        </c:if>
        <c:if test="${periodicalOperationType == 'update'}">
            <h1><fmt:message key="editPeriodical.title" bundle="${langPeriodical}"/></h1>
        </c:if>
        <h3><fmt:message key="title.top" bundle="${langPeriodical}"/></h3>

        <form class="form-horizontal" role="form"
              method="post"
              action="${ApplicationResources.PERIODICAL_CREATE_UPDATE_URI}">
            <div class="form-group hidden">
                <input id="entityId" type="text" class="form-control"
                       name="entityId"
                       value="<c:out value="${periodical.id}"/>"/>
            </div>
            <div class="form-group validated required">
                <label for="periodicalName" class="col-sm-3 control-label">
                    <fmt:message key="name.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <input id="periodicalName" type="text" class="form-control ajax-validated"
                           name="periodicalName"
                           value="<c:out value="${periodical.name}"/>"
                           placeholder="<fmt:message key="name.label" bundle="${langPeriodical}"/>"/>
                    <c:if test="${(not empty messages) && (not empty messages['periodicalName'])}">
                        <label class="messages
                            <c:out value="${messages['periodicalName'].type == 'ERROR' ? 'error' : ''}"/>">
                            <fmt:message key="${messages['periodicalName'].messageKey}" bundle="${validation}"/>
                        </label>
                    </c:if>
                </div>
            </div>
            <div class="form-group validated required">
                <label for="periodicalCategory" class="col-sm-3 control-label">
                    <fmt:message key="category.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <select id="periodicalCategory" class="form-control"
                            name="periodicalCategory">
                        <c:forEach items="${periodicalCategories}" var="category">
                            <option ${category == periodical.category ? 'selected' : ''}
                                    value="<c:out value='${category}'/>">
                                <fmt:message key="${category.messageKey}" bundle="${langPeriodical}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group validated required">
                <label for="periodicalPublisher" class="col-sm-3 control-label">
                    <fmt:message key="publisher.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <input id="periodicalPublisher" type="text" class="form-control ajax-validated"
                           name="periodicalPublisher"
                           value="<c:out value="${periodical.publisher}"/>"
                           placeholder="<fmt:message key="publisher.label" bundle="${langPeriodical}"/>"/>
                    <c:if test="${(not empty messages) && (not empty messages['periodicalPublisher'])}">
                        <label class="messages
                            <c:out value="${messages['periodicalPublisher'].type == 'ERROR' ? 'error' : ''}"/>">
                            <fmt:message key="${messages['periodicalPublisher'].messageKey}" bundle="${validation}"/>
                        </label>
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label for="periodicalDescription" class="col-sm-3 control-label">
                    <fmt:message key="description.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <textarea id="periodicalDescription" class="form-control" rows="4"
                              name="periodicalDescription" maxlength="999"
                              placeholder="<fmt:message key="description.label" bundle="${langPeriodical}"/>">
                        <c:out value="${periodical.description}"/>
                    </textarea>
                </div>
            </div>
            <div class="form-group validated required">
                <label for="periodicalCost" class="col-sm-3 control-label">
                    <fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <input id="periodicalCost" type="number" class="form-control ajax-validated"
                           name="periodicalCost" min="0" max="999999999"
                           value="<c:out value="${periodical.oneMonthCost}"/>"
                           placeholder="<fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/>"/>
                    <c:if test="${(not empty messages) && (not empty messages['periodicalCost'])}">
                        <label class="messages
                            <c:out value="${messages['periodicalCost'].type == 'ERROR' ? 'error' : ''}"/>">
                            <fmt:message key="${messages['periodicalCost'].messageKey}" bundle="${validation}"/>
                        </label>
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label for="periodicalStatus" class="col-sm-3 control-label">
                    <fmt:message key="status.label" bundle="${langPeriodical}"/></label>
                <div class="col-sm-9">
                    <select id="periodicalStatus" class="form-control"
                            name="periodicalStatus">
                        <c:forEach items="${periodicalStatuses}" var="status">
                            <option ${status == periodical.status ? 'selected' : ''}
                                    value="<c:out value='${status}'/>">
                                <fmt:message key="${status}" bundle="${langPeriodical}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group hidden">
                <input id="periodicalOperationType" type="text" class="form-control"
                       name="periodicalOperationType"
                       value="<c:out value="${periodicalOperationType}"/>"/>
            </div>
            <div class="form-group">
                <div class="col-md-9 col-md-offset-3">
                    <p class="requiredFieldsMessage">
                        <fmt:message key="requiredFieldsMessage" bundle="${general}"/></p>
                </div>
            </div>

            <div class="row text-center">
                <button type="submit"
                        class="btn btn-primary disabled">
                    <fmt:message key="savePeriodicalBtn.label" bundle="${langPeriodical}"/>
                </button>
                <a href="/backend/periodicals/${(periodicalOperationType == 'update') ? periodical.id : ''}"
                   class="btn btn-default" role="button">
                    <fmt:message key="cancelBtn.label" bundle="${general}"/>
                </a>
            </div>
        </form>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>