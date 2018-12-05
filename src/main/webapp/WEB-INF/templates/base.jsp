<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<!doctype html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" href="/resources/images/favicon.png" type="image/x-icon">
    <link rel="icon" href="/resources/images/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/base.css">

    <layout:block name="styles">
    </layout:block>

    <title>Bank</title>
</head>
<body>
    <div class="container-fluid" id="main-container">

        <div class="container">
            <header>
                <div class="row p-2">
                    <div class="col-md"><img src="/resources/images/logo.png" alt="Bank a. s." class="logo"></div>
                    <div class="col-md text-right align-self-center">Tel.: +720 123 456 789</div>
                </div>
            </header>
        </div>

        <layout:block name="content">
        </layout:block>

        <div class="container">
            <footer class="row justify-content-center pt-5 pb-3">
                <div class="col-md-4 text-center">
                    <img src="/resources/images/logo.png" alt="Bank a. s." class="logo"> <br /><br />
                    Copyright &copy; 2018, Bank, a. s.
                </div>
            </footer>
        </div>

    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="/resources/js/bootstrap.bundle.js"></script>

    <layout:block name="scripts">
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
    </layout:block>
</body>
</html>