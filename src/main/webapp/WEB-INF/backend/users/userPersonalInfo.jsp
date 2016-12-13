<div class="col-md-12">
    <h2><fmt:message key="user.yourPersonalInfo.sub-title" bundle="${langUser}"/></h2>
    <p><fmt:message key="user.yourPersonalInfo.description" bundle="${langUser}"/></p>
    <div class="col-xs-12 col-md-10 col-md-offset-1">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.username.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static"><c:out value="${thisUser.userName}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.firstName.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static"><c:out value="${thisUser.firstName}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.lastName.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static"><c:out value="${thisUser.lastName}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.email.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static"><c:out value="${thisUser.email}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.address.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static"><c:out value="${thisUser.address}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.birthday.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">
                        <fmt:formatDate type="date" value="${thisUser.birthday}"/></p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.roles.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <ul>
                        <c:forEach items="${thisUser.roles}" var="role">
                            <li><fmt:message key="${role}" bundle="${general}"/></li>
                        </c:forEach>
                    </ul></p>
                </div>
            </div>
        </form>
    </div>
</div>