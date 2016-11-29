<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="webProject.i18n.admin.general" var="general"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Java Training 2016</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/js/custom.js"></script>
</head>
<body>
<div class="container">
    <div id="header" class="row">
        <div class="col-md-4">
            <% if (session.getAttribute("thisUser") != null) {%>
            <p><span class="userFullName"><c:out value="${thisUser.lastName}"/>
            <c:out value="${thisUser.firstName}"/></span><br/>
                <span class="userEmail"><c:out value="${thisUser.email}"/></span></p>
            <a href="<c:url value="/adminPanel/users/currentUser"/>" class="btn btn-primary" role="button">
                <fmt:message key="myAccount.label" bundle="${general}"/></a>
            <p><a href="<c:url value="/Logout"/>">
                <fmt:message key="signout.label" bundle="${general}"/></a></p>
            <%} else {%>
            <p><a href="/login.jsp"><fmt:message key="signin.label" bundle="${general}"/></a></p>
            <%}%>
        </div>
        <div class="col-md-2 col-md-offset-6">
            <form>
                <select class="form-control" id="language" name="language" onchange="submit()">
                    <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}>English</option>
                    <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Українська</option>
                </select>
            </form>
        </div>

    </div>
