<%@include file="/WEB-INF/includes/general.jsp" %>
<fmt:setBundle basename="webProject.i18n.login.login" var="login"/>
<%@include file="/WEB-INF/includes/header.jsp" %>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="login.signin.title" bundle="${login}"/></h3>
                <form action="/Login" name="loginform"
                      method="POST" accept-charset="UTF-8" role="form">
                    <div class="form-group">
                        <label for="userName"><fmt:message key="login.username.label" bundle="${login}"/></label>
                        <input type="text" class="form-control" id="userName"
                               placeholder="<fmt:message key="login.username.label" bundle="${login}"/>"
                               name="username"
                        value="${sessionScope.username}">
                    </div>
                    <div class="form-group">
                        <label for="userPassword"><fmt:message key="login.password.label" bundle="${login}"/></label>
                        <input type="password" class="form-control" id="userPassword"
                               placeholder="<fmt:message key="login.password.label" bundle="${login}"/>"
                               name="password">
                    </div>


                    <button type="submit" class="btn btn-lg btn-success btn-block"><fmt:message key="login.signin.label"
                                                                                                bundle="${login}"/></button>
                </form>
            </div>

        </div>
    </div>
</div>


<%@include file="/WEB-INF/includes/footer.jsp" %>

