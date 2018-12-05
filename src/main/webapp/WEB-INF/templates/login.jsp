<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>

<layout:extends name="base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="/resources/css/login.css">
    </layout:put>

    <layout:put block="content">

        <div class="container-fluid">
            <div class="row login-wrapper lb-content-wrapper justify-content-center">
                <form class="col-lg-3 col-md-6 col-12 align-self-center" id="account-login-form">
                    <div class="form-group">
                        <label for="UID">Identifikační číslo:</label>
                        <div class="input-group">
                            <input type="number" class="form-control" id="UID" placeholder="př.: 34512685">
                            <div class="input-group-append">
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="Zadejte své identifikační číslo pro Internetové bankovnictví.">i</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password">Heslo:</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password">
                            <div class="input-group-append">
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="Zadejte své heslo pro Internetové bankovnictví.">i</span>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" data-toggle="button" aria-pressed="false">Přihlásit</button>
                </form>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>