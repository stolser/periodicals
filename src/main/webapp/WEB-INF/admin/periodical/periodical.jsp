<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <h1><fmt:message key="title.top" bundle="${langPeriodical}"/></h1>
    <div class="col-md-8 col-md-offset-2">
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

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>