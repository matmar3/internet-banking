<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container-fluid lb-content-wrapper">
    <div class="container">
        <div class="row justify-content-center">
            <form:form class="col-lg-10 col-12 align-self-center" action="/admin/profile" method="post" modelAttribute="modifiedUser" >
                <div class="form-row">
                    <div class="form-group col-md-5 col-12">
                        <div class="h3">Osobní údaje</div>
                        <div class="form-group">
                            <form:label path="firstName">Křestní jméno: *</form:label>
                            <form:input type="text" class="form-control" path="firstName" required="required" maxlength="20" readonly="true" />
                        </div>
                        <div class="form-group">
                            <form:label path="lastName">Příjmení: *</form:label>
                            <form:input type="text" class="form-control" path="lastName" required="required" maxlength="30" readonly="true" />
                        </div>
                        <div class="form-group">
                            <form:label path="birthNumber">Rodné číslo: *</form:label>
                            <form:input type="text" class="form-control" path="birthNumber" required="required" maxlength="20" readonly="true" />
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
                        <input type="submit" class="btn btn-primary" value="Uložit změny"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>