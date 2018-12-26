<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="../css/registration.css">
    </layout:put>

    <layout:put block="content">

        <div class="container">
            <nav>
                <div class="nav">
                    <ul class="nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/index">Zákaznické účty</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="/admin/create-user">Založit účet</a>
                        </li>
                    </ul>
                    <ul class="nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">User 001</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Nastavení</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="/logout">Odhlásit se <i class="fas fa-sign-out-alt"></i></a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row justify-content-center">
                    <form:form class="col-lg-10 col-12 align-self-center" action="/admin/create-user" method="post" modelAttribute="newUser" id="account-registration-form">
                        <div class="form-row">
                            <div class="form-group col-md-5 col-12">
                                <div class="h3">Osobní údaje</div>
                                <div class="form-group">
                                    <form:label path="firstName">Křestní jméno: *</form:label><form:errors class="formError" path='firstName' />
                                    <form:input type="text" class="form-control" path="firstName" required="required" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <form:label path="lastName">Příjmení: *</form:label><form:errors class="formError" path='lastName' />
                                    <form:input type="text" class="form-control" path="lastName" required="required" maxlength="30" />
                                </div>
                                <div class="form-group">
                                    <form:label path="birthNumber">Rodné číslo: *</form:label><form:errors class="formError" path='birthNumber' />
                                    <form:input type="text" class="form-control" path="birthNumber" required="required" maxlength="20" />
                                </div>
                                <div class="form-group">
                                    <form:label path="birthDate">Datum narození:</form:label><form:errors class="formError" path='birthDate' />
                                    <form:input type="date" class="form-control" path="birthDate" />
                                </div>
                            </div>
                            <div class="form-group col-md-5 offset-md-2 col-12">
                                <div class="h3">Kontaktní údaje</div>
                                <div class="form-group">
                                    <form:label path="email">E-mail: *</form:label><form:errors class="formError" path='email' />
                                    <form:input type="email" class="form-control" path="email" required="required" maxlength="50" />
                                </div>
                                <div class="form-group">
                                    <form:label path="mobileNumber">Mobilní telefon:</form:label><form:errors class="formError" path='mobileNumber' />
                                    <form:input type="text" class="form-control" path="mobileNumber" maxlength="16"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="street">Ulice:</form:label><form:errors class="formError" path='street' />
                                    <form:input type="text" class="form-control" path="street" maxlength="30" />
                                </div>
                                <div class="form-group">
                                    <form:label path="houseNumber">Číslo:</form:label><form:errors class="formError" path='houseNumber' />
                                    <form:input type="text" class="form-control col-3" path="houseNumber" maxlength="5"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="zipCode">PSČ:</form:label><form:errors class="formError" path='zipCode' />
                                    <form:input type="text" class="form-control col-3" path="zipCode" maxlength="6" />
                                </div>
                                <div class="form-group">
                                    <form:label path="city">Město:</form:label><form:errors class="formError" path='city' />
                                    <form:input type="text" class="form-control" path="city" maxlength="30" />
                                </div>
                            </div>
                            <div class="form-group col-12 text-center mt-sm-5">
                                <div class="form-inline form-check justify-content-center mb-sm-3">
                                    <form:checkbox class="form-check-input" path="terms_conditions" required="required" />
                                    <form:label class="form-check-label" path="terms_conditions"> * Souhlasím se&nbsp;<a class="alert-link" href="#">smluvními podmínkami</a>.</form:label>
                                    <form:errors class="formError" path='terms_conditions' />
                                </div>
                                <input type="submit" class="btn btn-primary" value="Založit účet"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>