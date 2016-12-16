<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>

<p class="text-right"><span class="userFullName"><c:out value="${thisUser.lastName}"/>
            <c:out value="${thisUser.firstName}"/></span></p>
<p><a href="<c:url value="/backend/users/currentUser"/>" class="btn btn-primary" role="button">
    <fmt:message key="myAccount.label" bundle="${langGeneral}"/></a></p>
<p><a href="<c:url value="/backend/signOut"/>">
    <fmt:message key="signout.label" bundle="${langGeneral}"/></a></p>

