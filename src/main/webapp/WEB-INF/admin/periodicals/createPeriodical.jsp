<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langUser"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1><fmt:message key="createPeriodical.title" bundle="${langUser}"/></h1>
        <h3><fmt:message key="title.top" bundle="${langUser}"/></h3>
        <p><fmt:message key="id.label" bundle="${langUser}"/>:
            <c:out value="${periodical.id}"/></p>
        <p><fmt:message key="name.label" bundle="${langUser}"/>:
            <c:out value="${periodical.name}"/></p>
        <p><fmt:message key="category.label" bundle="${langUser}"/>:
            <c:out value="${periodical.category}"/></p>
        <p><fmt:message key="publisher.label" bundle="${langUser}"/>:
            <c:out value="${periodical.publisher}"/></p>
        <p><fmt:message key="description.label" bundle="${langUser}"/>:
            <c:out value="${periodical.description}"/></p>
        <p><fmt:message key="oneMonthCost.label" bundle="${langUser}"/>:
            <c:out value="${periodical.oneMonthCost}"/></p>
        <p><fmt:message key="status.label" bundle="${langUser}"/>:
            <c:out value="${periodical.status}"/></p>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>