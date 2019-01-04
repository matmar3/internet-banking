<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container-fluid lb-content-wrapper">
    <div class="container">
        <div class="row justify-content-center">
            <form:form class="col-lg-10 col-12 align-self-center" action="${modifyUserActionUrl}" method="post" modelAttribute="modifiedUser" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-row">
                    <div class="form-group col-md-5 col-12">
                        <div class="h3"><spring:message code="profile.edit.form.personalData" /></div>
                        <div class="form-group">
                            <form:label path="firstName"><spring:message code="profile.edit.form.firstNameRequired" /></form:label>
                            <form:input type="text" class="form-control" path="firstName" required="required" maxlength="20" readonly="true" />
                        </div>
                        <div class="form-group">
                            <form:label path="lastName"><spring:message code="profile.edit.form.lastNameRequired" /></form:label>
                            <form:input type="text" class="form-control" path="lastName" required="required" maxlength="30" readonly="true" />
                        </div>
                        <div class="form-group">
                            <form:label path="birthNumber"><spring:message code="profile.edit.form.personalIdentificationNumberRequired" /></form:label>
                            <form:input type="text" class="form-control" path="birthNumber" required="required" maxlength="20" readonly="true" />
                        </div>
                        <div class="form-group">
                            <form:label path="birthDate"><spring:message code="profile.edit.form.birthDate" /></form:label><form:errors class="formError" path='birthDate' />
                            <form:input type="date" class="form-control" path="birthDate" />
                        </div>
                    </div>
                    <div class="form-group col-md-5 offset-md-2 col-12">
                        <div class="h3"><spring:message code="profile.edit.form.contactData" /></div>
                        <div class="form-group">
                            <form:label path="email"><spring:message code="profile.edit.form.emailRequired" /></form:label><form:errors class="formError" path='email' />
                            <form:input type="email" class="form-control" path="email" required="required" maxlength="50" />
                        </div>
                        <div class="form-group">
                            <form:label path="mobileNumber"><spring:message code="profile.edit.form.phoneNumber" /></form:label><form:errors class="formError" path='mobileNumber' />
                            <form:input type="text" class="form-control" path="mobileNumber" maxlength="16"/>
                        </div>
                        <div class="form-group">
                            <form:label path="street"><spring:message code="profile.edit.form.street" /></form:label><form:errors class="formError" path='street' />
                            <form:input type="text" class="form-control" path="street" maxlength="30" />
                        </div>
                        <div class="form-group">
                            <form:label path="houseNumber"><spring:message code="profile.edit.form.houseNumber" /></form:label><form:errors class="formError" path='houseNumber' />
                            <form:input type="text" class="form-control col-3" path="houseNumber" maxlength="5"/>
                        </div>
                        <div class="form-group">
                            <form:label path="zipCode"><spring:message code="profile.edit.form.zipCode" /></form:label><form:errors class="formError" path='zipCode' />
                            <form:input type="text" class="form-control col-3" path="zipCode" maxlength="6" />
                        </div>
                        <div class="form-group">
                            <form:label path="city"><spring:message code="profile.edit.form.city" /></form:label><form:errors class="formError" path='city' />
                            <form:input type="text" class="form-control" path="city" maxlength="30" />
                        </div>
                    </div>
                    <div class="form-group col-12 text-center mt-sm-5">
                        <spring:message code="profile.edit.form.saveChanges" var="saveChangesLabel" />
                        <input type="submit" class="btn btn-primary" value="${saveChangesLabel}"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>