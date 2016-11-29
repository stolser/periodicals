<%@include file="/WEB-INF/includes/general.jsp" %>
<fmt:setBundle basename="webProject.i18n.login.login" var="login"/>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<%@include file="/WEB-INF/includes/header.jsp" %>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="login.signin.title" bundle="${login}"/></h3>
                <form action="/Login" name="loginform"
                      method="POST" accept-charset="UTF-8" role="form">
                    <div class="form-group validated">
                        <label for="userName"><fmt:message key="login.username.label" bundle="${login}"/></label>
                        <input type="text" class="form-control" id="userName"
                               placeholder="<fmt:message key="login.username.label" bundle="${login}"/>"
                               name="signInUsername"
                               value="${sessionScope.username}"/>
                        <label class="validationMessage"></label>
                        <c:if test="${(not empty messages) && (not empty messages['signInUsername'])}">
                            <label class="messages
                            <c:out value="${messages['signInUsername'].type == 'ERROR' ? 'error' : ''}"/>">
                                <fmt:message key="${messages['signInUsername'].messageKey}" bundle="${validation}"/>
                            </label>
                        </c:if>

                    </div>
                    <div class="form-group validated">
                        <label for="userPassword"><fmt:message key="login.password.label" bundle="${login}"/></label>
                        <input type="password" class="form-control" id="userPassword"
                               placeholder="<fmt:message key="login.password.label" bundle="${login}"/>"
                               name="password"/>
                        <c:if test="${(not empty messages) && (not empty messages['password'])}">
                            <label class="messages
                            <c:out value="${messages['password'].type == 'ERROR' ? 'error' : ''}"/>">
                                <fmt:message key="${messages['password'].messageKey}" bundle="${validation}"/>
                            </label>
                        </c:if>
                    </div>


                    <button type="submit" class="btn btn-lg btn-success btn-block"><fmt:message key="login.signin.label"
                                                                                                bundle="${login}"/></button>
                </form>
            </div>

        </div>
    </div>
</div>

<script>

</script>


<%@include file="/WEB-INF/includes/footer.jsp" %>

