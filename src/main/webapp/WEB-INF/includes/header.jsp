<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="general.jsp" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>
<fmt:setBundle basename="webProject.i18n.validation" var="langValidation"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="htmlHead.title" bundle="${general}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/custom.js"></script>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="container">
    <div id="header" class="row">
        <div class="col-xs-6 col-md-2 col-md-push-8">
            <form>
                <select class="form-control" id="language" name="language" onchange="submit()">
                    <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}>English</option>
                    <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Українська</option>
                </select>
            </form>
        </div>
        <div class="col-xs-6 col-md-2 col-md-push-8 text-right">
            <% if (session.getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME) != null) {%>
            <p class="text-right"><span class="userFullName"><c:out value="${thisUser.lastName}"/>
            <c:out value="${thisUser.firstName}"/></span><br/>
                <span class="userEmail"><c:out value="${thisUser.email}"/></span></p>
            <a href="<c:url value="/backend/users/currentUser"/>" class="btn btn-primary" role="button">
                <fmt:message key="myAccount.label" bundle="${general}"/></a>

            <%} else if (!"/login.jsp".equals(request.getRequestURI())) {%>
            <p><a href="/login.jsp"><fmt:message key="signin.label" bundle="${general}"/></a></p>
            <%}%>
        </div>

        <div class="col-xs-12 col-md-8 col-md-pull-4">
            <% if (session.getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME) != null) {%>
            <nav class="navbar navbar-default navbar-static-top">
                <div class="container-fluid">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/backend/users/currentUser">
                            <fmt:message key="myAccount.label" bundle="${general}"/></a></li>
                        <li><a href="/backend/periodicals">
                            <fmt:message key="menu.periodicals.label" bundle="${general}"/></a></li>
                        <custom:if-authorized mustHaveRoles="admin">
                            <li><a href="/backend/users">
                                <fmt:message key="menu.users.label" bundle="${general}"/></a></li>
                        </custom:if-authorized>
                        <li><a href="/backend/signOut">
                            <fmt:message key="signout.label" bundle="${general}"/></a></li>
                    </ul>
                </div>
            </nav>
            <%}%>
        </div>




    </div>


    <c:if test="${(not empty messages) && (not empty messages['topMessages'])}">
    <div class="row">
        <div class="col-md-12">
            <c:forEach items="${messages['topMessages']}" var="message">
                <div class="topMessages alert
                ${message.type == 'SUCCESS' ? 'alert-success' :
                    (message.type == 'INFO' ? 'alert-info' :
                    (message.type == 'ERROR' ? 'alert-danger' : ''))}" role="alert">
                    <fmt:message key="${message.messageKey}" bundle="${langValidation}"/>
                </div>
            </c:forEach>
        </div>
    </div>
    </c:if>

