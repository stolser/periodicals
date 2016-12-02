<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-8 col-md-offset-2">
        <h1><c:out value="${periodical.name}"/></h1>
        <h3><fmt:message key="title.top" bundle="${langPeriodical}"/></h3>
        <p><fmt:message key="id.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.id}"/></p>
        <p><fmt:message key="name.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.name}"/></p>
        <p><fmt:message key="category.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.category}"/></p>
        <p><fmt:message key="publisher.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.publisher}"/></p>
        <p><fmt:message key="description.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.description}"/></p>
        <p><fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.oneMonthCost}"/></p>
        <p><fmt:message key="status.label" bundle="${langPeriodical}"/>:
            <c:out value="${periodical.status}"/></p>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>