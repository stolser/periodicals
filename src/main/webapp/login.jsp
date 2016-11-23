<%@include file="pages/includes/general.jsp" %>
<fmt:setBundle basename="webProject.i18n.login.login" var="login"/>
<%@include file="pages/includes/header.jsp" %>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="login.signin.title" bundle="${login}"/></h3>
                <form name="loginform" action="" method="POST" accept-charset="UTF-8" role="form">
                    <div class="form-group">
                        <label for="userName"><fmt:message key="login.username.label" bundle="${login}"/></label>
                        <input type="text" class="form-control" id="userName"
                               placeholder="<fmt:message key="login.username.label" bundle="${login}"/>"
                               name="username">
                    </div>
                    <div class="form-group">
                        <label for="userPassword"><fmt:message key="login.password.label" bundle="${login}"/></label>
                        <input type="password" class="form-control" id="userPassword"
                               placeholder="<fmt:message key="login.password.label" bundle="${login}"/>"
                               name="password">
                    </div>

                    <div class="form-group">
                        <label for="userEmail"><fmt:message key="login.email.label" bundle="${login}"/></label>
                        <input type="email" class="form-control" id="userEmail"
                               placeholder="<fmt:message key="login.email.label" bundle="${login}"/>"
                               name="userEmail">
                    </div>

                    <div class="checkbox">
                        <label>
                            <input name="rememberMe" type="checkbox" value="true">
                            <fmt:message key="login.rememberMe.label" bundle="${login}"/>
                        </label>
                    </div>

                    <button type="submit" class="btn btn-lg btn-success btn-block"><fmt:message key="login.signin.label"
                                                                                                bundle="${login}"/></button>
                </form>
            </div>
            <div class="panel-body">

            </div>
        </div>
    </div>
</div>


<%@include file="pages/includes/footer.jsp" %>

