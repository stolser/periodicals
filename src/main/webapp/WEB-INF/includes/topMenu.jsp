<fmt:setBundle basename="webproject.i18n.backend.general" var="general"/>

<nav id="mainMenu" class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <custom:if-authorized mustHaveRoles="admin">
                <li><a href="<c:url value="${ApplicationResources.ADMIN_PANEL_URI}"/>">
                    <fmt:message key="menu.adminPanel.label" bundle="${general}"/></a></li>
            </custom:if-authorized>
            <li><a href="<c:url value="${ApplicationResources.PERIODICAL_LIST_URI}"/>">
                <fmt:message key="menu.periodicals.label" bundle="${general}"/></a></li>
            <custom:if-authorized mustHaveRoles="admin">
                <li><a href="<c:url value="${ApplicationResources.USERS_LIST_URI}"/>">
                    <fmt:message key="menu.users.label" bundle="${general}"/></a></li>
            </custom:if-authorized>
        </ul>
    </div>
</nav>
