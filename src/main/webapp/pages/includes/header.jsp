<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Java Training 2016</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
<div id="header" class="row">
    <div class="col-md-4 col-md-offset-8">
        <form>
            <select class="form-control" id="language" name="language" onchange="submit()">
                <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}>English</option>
                <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>Українська</option>
            </select>
        </form>
    </div>

</div>
