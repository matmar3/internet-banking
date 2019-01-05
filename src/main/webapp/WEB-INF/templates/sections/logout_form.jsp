<%@ page contentType="text/html; charset=UTF-8" %>

<form id='logout-form' action='/logout' method='post'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>