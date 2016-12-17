<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="http://stolser.com/javaee/jsp-tags-0.1" %>
<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>
<%@ page import="com.stolser.javatraining.webproject.controller.ApplicationResources" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="htmlHead.title" bundle="${langGeneral}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/custom.css">
    <script src="/js/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/custom.js"></script>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="container">
    <div id="header" class="row">
        <div class="col-xs-6 col-md-2 col-md-push-6">
            <form>
                <select class="form-control" id="language" name="language" onchange="submit()">
                    <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}>English</option>
                    <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Українська</option>
                </select>
            </form>
        </div>
        <div class="col-xs-6 col-md-4 col-md-push-6 text-right">
            <% if (session.getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME) != null) {%>
            <%@include file="/WEB-INF/includes/topUserInfo.jsp" %>
            <%} else if (!"/login.jsp".equals(request.getRequestURI())) {%>
            <p><a href="/login.jsp"><fmt:message key="signin.label" bundle="${langGeneral}"/></a></p>
            <%}%>
        </div>

        <div class="col-xs-12 col-md-6 col-md-pull-6">
            <% if (session.getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME) != null) {%>
            <%@include file="/WEB-INF/includes/topMenu.jsp" %>
            <%}%>
        </div>
    </div>

    <c:if test="${(not empty messages) && (not empty messages['topMessages'])}">
        <%@include file="/WEB-INF/includes/topMessagesBlock.jsp" %>
    </c:if>

