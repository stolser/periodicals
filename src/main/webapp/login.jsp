<%@include file="/WEB-INF/includes/general.jsp" %>
<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.credential.credential" var="credential"/>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="credential.signin.title" bundle="${credential}"/></h3>
                <form method="POST" name="loginform" id="loginform"
                      action="/signIn"
                      accept-charset="UTF-8" role="form">
                    <div class="form-group validated required">
                        <label class="control-label" for="userName">
                            <fmt:message key="credential.username.label" bundle="${credential}"/></label>
                        <input type="text" class="form-control" id="userName"
                               placeholder="<fmt:message key="credential.username.label" bundle="${credential}"/>"
                               name="signInUsername"
                               value="${sessionScope.username}"/>
                        <c:if test="${(not empty messages) && (not empty messages['signInUsername'])}">
                            <label class="messages
                            <c:out value="${messages['signInUsername'].type == 'ERROR' ? 'error' : ''}"/>">
                                <fmt:message key="${messages['signInUsername'].messageKey}" bundle="${validation}"/>
                            </label>
                        </c:if>

                    </div>
                    <div class="form-group validated required">
                        <label class="control-label" for="userPassword">
                            <fmt:message key="credential.password.label" bundle="${credential}"/></label>
                        <input type="password" class="form-control" id="userPassword"
                               placeholder="<fmt:message key="credential.password.label" bundle="${credential}"/>"
                               name="password"/>
                        <c:if test="${(not empty messages) && (not empty messages['password'])}">
                            <label class="messages
                            <c:out value="${messages['password'].type == 'ERROR' ? 'error' : ''}"/>">
                                <fmt:message key="${messages['password'].messageKey}" bundle="${validation}"/>
                            </label>
                        </c:if>
                    </div>
                    <p class="requiredFieldsMessage"><fmt:message key="requiredFieldsMessage" bundle="${general}"/></p>


                    <button type="submit"
                            class="btn btn-lg btn-primary btn-block disabled">
                        <fmt:message key="credential.signin.label" bundle="${credential}"/></button>
                </form>
            </div>

        </div>
    </div>
</div>

<script>

</script>


<%@include file="/WEB-INF/includes/footer.jsp" %>

