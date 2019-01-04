<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="/css/login.css">
    </layout:put>

    <layout:put block="content">

        <div class="container-fluid">
            <div class="row login-wrapper lb-content-wrapper justify-content-center">
                <form class="col-lg-3 col-md-6 col-12 align-self-center" action="/login-process" method="post" id="account-login-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <label for="UID"><spring:message code="login.form.uid" /></label>
                        <div class="input-group">
                            <spring:message code="login.form.uid.placeholder" var="uidPlaceholder" />
                            <input type="text" class="form-control" id="UID" name="username" placeholder="${uidPlaceholder}">
                            <div class="input-group-append">
                                <spring:message code="login.form.uid.tooltip" var="uidTooltip" />
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="${uidTooltip}"><spring:message code="tooltip.label" /></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password"><spring:message code="login.form.password" /></label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password">
                            <div class="input-group-append">
                                <spring:message code="login.form.password.tooltip" var="passTooltip" />
                                <span class="input-group-text" data-toggle="tooltip" data-placement="right" title="${passTooltip}"><spring:message code="tooltip.label" /></span>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary"><spring:message code="login.form.submit" /></button>
                </form>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>