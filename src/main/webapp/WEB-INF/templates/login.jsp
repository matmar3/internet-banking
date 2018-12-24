<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="css/login.css">
    </layout:put>

    <layout:put block="content">

        <div class="container-fluid">
            <c:if test="${not empty errorMessage}" >
                <div class="row justify-content-center">
                    <div class="alert alert-warning" role="alert">
                            ${errorMessage}
                    </div>
                </div>
            </c:if>
            <div class="row login-wrapper lb-content-wrapper justify-content-center">
                <form class="col-lg-3 col-md-6 col-12 align-self-center" action="/login-process" method="post" id="account-login-form">
                    <div class="form-group">
                        <label for="UID">Identifikační číslo:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="UID" name="username" placeholder="př.: 34512685">
                            <div class="input-group-append">
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="Zadejte své identifikační číslo pro Internetové bankovnictví.">i</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password">Heslo:</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password">
                            <div class="input-group-append">
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="Zadejte své heslo pro Internetové bankovnictví.">i</span>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Přihlásit</button>
                </form>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>