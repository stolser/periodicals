<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row text-center">
    <div class="col-md-12">
        <h1><fmt:message key="page_404.toptitle" bundle="${general}"/></h1>
    </div>
    <div class="col-md-8 col-md-offset-2">
        <h2><fmt:message key="page_404.sub_title" bundle="${general}"/></h2>
        <p><fmt:message key="sql-error-page.description" bundle="${general}"/>
            <a href="/backend/users/currentUser"><fmt:message key="page_404.homepage.label" bundle="${general}"/></a>.</p>
    </div>

</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>