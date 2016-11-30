<%@include file="../../includes/general.jsp" %>
<%@include file="../../includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.periodical" var="langPeriodical"/>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="periodicalList.title.top" bundle="${langPeriodical}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="id.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="name.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="category.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="publisher.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="oneMonthCost.label" bundle="${langPeriodical}"/></th>
                <th><fmt:message key="status.label" bundle="${langPeriodical}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allPeriodicals}" var="periodical" varStatus="rowStatus">
                <tr
                        <c:if test="${periodical.status == 'VISIBLE'}">class="success"</c:if>
                        <c:if test="${periodical.status == 'INVISIBLE'}">class="warning"</c:if>
                        <c:if test="${periodical.status == 'DISCARDED'}">class="danger"</c:if>>
                    <td><c:out value="${periodical.id}"/></td>
                    <td><c:out value="${periodical.name}"/></td>
                    <td><c:out value="${periodical.category}"/></td>
                    <td><c:out value="${periodical.publisher}"/></td>
                    <td><c:out value="${periodical.oneMonthCost}"/></td>
                    <td><c:out value="${periodical.status}"/></td>
                </tr>

            </c:forEach>
            </tbody>

        </table>

    </div>

</div>

<%@include file="../../includes/footer.jsp" %>