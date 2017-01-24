<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>

<p class="text-right"><span class="userFullName">${currentUser.lastName} ${currentUser.firstName}</span>
    <a href="<c:url value="${ApplicationResources.CURRENT_USER_ACCOUNT_URI}"/>"
       class="btn btn-primary myAccountBtn" role="button">
    <fmt:message key="myAccount.label" bundle="${langGeneral}"/></a>
    <a href="<c:url value="${ApplicationResources.SIGN_OUT_URI}"/>">
    <fmt:message key="signout.label" bundle="${langGeneral}"/></a></p>

