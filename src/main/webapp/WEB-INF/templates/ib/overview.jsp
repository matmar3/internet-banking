<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="/css/account.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7 col-md-6 col-12">
                        <div class="h3"><spring:message code="ib.index.overviewLabel" /></div>
                        <table class="table table-hover table-sm">
                            <thead>
                                <tr>
                                    <th><spring:message code="ib.index.table.accountNumber" /></th>
                                    <th><spring:message code="ib.index.table.cardNumber" /></th>
                                    <th><spring:message code="ib.index.table.balance" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty accounts}">
                                        <spring:message code="ib.index.table.row.tooltip" var="rowTooltip" />
                                        <c:forEach items="${accounts}" var="account">
                                            <tr class="row-link" data-href="/ib/account/${account.id}" data-toggle="tooltip" data-placement="top" title="${rowTooltip}">
                                                <td>${account.accountNumber}</td>
                                                <td>${account.cardNumber}</td>
                                                <td>${account.formatedBalance}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="3"><spring:message code="ib.index.table.noData" /></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-lg-5 col-md-6 col-12">
                        <form:form class="col-12 align-self-center" action="/ib/create-account" method="post" modelAttribute="newAccount">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-row">
                                <div class="form-group col-12">
                                    <div class="h3"><spring:message code="account.create.form.label" /></div>
                                    <div class="form-group">
                                        <form:label path="currency"><spring:message code="account.create.form.currencyRequired" /></form:label><form:errors class="formError" path='currency' />
                                        <form:select class="form-control" path="currency" items="${currencies}" required="required" />
                                    </div>
                                </div>
                                <div class="form-group col-12 text-center mt-sm-5">
                                    <spring:message code="account.create.form.createAccount" var="createAccountLabel" />
                                    <input type="submit" class="btn btn-primary" value="${createAccountLabel}"/>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">
        <script>
            $(".row-link").click(function() {
                window.location = $(this).data("href");
            });
        </script>
    </layout:put>
</layout:extends>