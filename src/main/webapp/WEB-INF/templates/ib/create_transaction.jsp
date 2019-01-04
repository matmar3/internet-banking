<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row justify-content-center">
                    <form:form class="col-lg-10 col-12 align-self-center" action="/ib/create-transaction" method="post" modelAttribute="newTransaction" >
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-row">
                            <div class="form-group col-12">
                                <div class="form-group">
                                    <label for="transactionTemplate"><spring:message code="transaction.create.form.template" /></label>
                                    <select id="transactionTemplate">
                                        <option value="">--</option>
                                        <c:forEach var="template" items="${templates}">
                                            <option value="${template.id}">${template.templateName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-5 col-12">
                                <div class="h3"><spring:message code="transaction.create.form.paymentSettings" /></div>
                                <div class="form-group">
                                    <form:label path="senderAccountNumber"><spring:message code="transaction.create.form.senderAccountRequired" /></form:label><form:errors class="formError" path='senderAccountNumber' />
                                    <form:select class="form-control" items="${accounts}" path="senderAccountNumber" required="required" />
                                </div>
                                <div class="form-group">
                                    <form:label path="receiverAccountNumber"><spring:message code="transaction.create.form.receiverAccountRequired" /></form:label><form:errors class="formError" path='receiverAccountNumber' />
                                    <form:input type="text" class="form-control" path="receiverAccountNumber" required="required" pattern="^(([0-9]{6})-)?([0-9]{10})(/[0-9]{4})$" />
                                </div>
                                <div class="form-group">
                                    <form:label path="sentAmount"><spring:message code="transaction.create.form.amountRequired" /></form:label><form:errors class="formError" path='sentAmount' />
                                    <form:input type="number" class="form-control" path="sentAmount" required="required" min="1" />
                                </div>
                                <div class="form-group">
                                    <form:label path="dueDate"><spring:message code="transaction.create.form.dueDate" /></form:label><form:errors class="formError" path='dueDate' />
                                    <form:input type="date" class="form-control" path="dueDate" />
                                </div>
                            </div>
                            <div class="form-group col-md-5 offset-md-2 col-12">
                                <div class="h3"><spring:message code="transaction.create.form.additionalData" /></div>
                                <div class="form-group">
                                    <form:label path="constantSymbol"><spring:message code="transaction.create.form.constantSymbol" /></form:label><form:errors class="formError" path='constantSymbol' />
                                    <form:input type="text" class="form-control" path="constantSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="variableSymbol"><spring:message code="transaction.create.form.variableSymbol" /></form:label><form:errors class="formError" path='variableSymbol' />
                                    <form:input type="text" class="form-control" path="variableSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="specificSymbol"><spring:message code="transaction.create.form.specificSymbol" /></form:label><form:errors class="formError" path='specificSymbol' />
                                    <form:input type="text" class="form-control" path="specificSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="message"><spring:message code="transaction.create.form.message" /></form:label><form:errors class="formError" path='message' />
                                    <form:input type="text" class="form-control" path="message" maxlength="255" />
                                </div>
                            </div>
                            <div class="form-group col-12 text-center mt-sm-5">
                                <spring:message code="transaction.create.form.performPayment" var="performPaymentLabel" />
                                <button type="submit" class="btn btn-primary">${performPaymentLabel}</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">
        <script src="/js/async_templates.js"></script>
    </layout:put>
</layout:extends>