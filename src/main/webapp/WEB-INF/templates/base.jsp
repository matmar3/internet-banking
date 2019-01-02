<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="/images/favicon.png" type="image/x-icon">
    <link rel="icon" href="/images/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/base.css">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">

    <layout:block name="styles">
    </layout:block>

    <title>Bank</title>
</head>
<body>
    <div class="container-fluid" id="main-container">

        <div class="container">
            <header>
                <div class="row p-2">
                    <div class="col-md"><img src="/images/logo.png" alt="Bank a. s." class="logo"></div>
                    <div class="col-md text-right align-self-center">Tel.: +720 123 456 789</div>
                </div>
            </header>
        </div>

        <c:if test="${not empty message}" >
            <div class="container">
                <div class="row justify-content-center">
                    <div class="alert alert-${message.type.value}" role="alert">
                            ${message.content}
                    </div>
                </div>
            </div>
        </c:if>

        <layout:block name="content">
        </layout:block>

        <div class="container">
            <footer class="row justify-content-center pt-5 pb-3">
                <div class="col-md-4 text-center">
                    <img src="/images/logo.png" alt="Bank a. s." class="logo"> <br /><br />
                    Copyright &copy; 2018, Bank, a. s.
                </div>
            </footer>
        </div>

    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <script src="/js/bootstrap.bundle.js"></script>

    <layout:block name="scripts">
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
    </layout:block>
</body>
</html>