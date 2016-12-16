<fmt:setBundle basename="webProject.i18n.validation" var="langValidation"/>

<div class="row">
    <div class="col-md-12">
        <c:forEach items="${messages['topMessages']}" var="message">
            <div class="topMessages alert
                ${message.type == 'SUCCESS' ? 'alert-success' :
                    (message.type == 'INFO' ? 'alert-info' :
                    (message.type == 'ERROR' ? 'alert-danger' :
                    (message.type == 'WARNING' ? 'alert-warning' : '')))}" role="alert">
                <fmt:message key="${message.messageKey}" bundle="${langValidation}"/>
            </div>
        </c:forEach>
    </div>
</div>