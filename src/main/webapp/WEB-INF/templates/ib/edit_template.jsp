<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="../../css/account.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row justify-content-center mt-5">
                    <div class="h3">Upravit šablonu</div>
                    <form:form class="col-lg-10 col-12 align-self-center" action="/ib/templates/modify" method="post" modelAttribute="modifyTemplate" >
                        <div class="form-row">
                            <div class="form-group col-12">
                                <div class="form-group">
                                    <form:label path="templateName">Název šablony: *</form:label><form:errors class="formError" path='templateName' />
                                    <form:input type="text" class="form-control" path="templateName" required="required" />
                                </div>
                            </div>
                            <div class="form-group col-md-5 col-12">
                                <div class="h4">Nastavení převodu</div>
                                <div class="form-group">
                                    <form:label path="senderAccountNumber">Z účtu: *</form:label><form:errors class="formError" path='senderAccountNumber' />
                                    <form:select class="form-control" items="${accounts}" path="senderAccountNumber" required="required" />
                                </div>
                                <div class="form-group">
                                    <form:label path="receiverAccountNumber">Účet příjemce: *</form:label><form:errors class="formError" path='receiverAccountNumber' />
                                    <form:input type="text" class="form-control" path="receiverAccountNumber" required="required" minlength="15" maxlength="22" />
                                </div>
                                <div class="form-group">
                                    <form:label path="sentAmount">Částka: *</form:label><form:errors class="formError" path='sentAmount' />
                                    <form:input type="number" class="form-control" path="sentAmount" required="required" min="1" />
                                </div>
                                <div class="form-group">
                                    <form:label path="dueDate">Datum provedení platby:</form:label><form:errors class="formError" path='dueDate' />
                                    <form:input type="date" class="form-control" path="dueDate" />
                                </div>
                            </div>
                            <div class="form-group col-md-5 offset-md-2 col-12">
                                <div class="h4">Doplňující údaje</div>
                                <div class="form-group">
                                    <form:label path="constantSymbol">Konstantní symbol:</form:label><form:errors class="formError" path='constantSymbol' />
                                    <form:input type="text" class="form-control" path="constantSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="variableSymbol">Variabilní symbol:</form:label><form:errors class="formError" path='variableSymbol' />
                                    <form:input type="text" class="form-control" path="variableSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="specificSymbol">Specifický symbol:</form:label><form:errors class="formError" path='specificSymbol' />
                                    <form:input type="text" class="form-control" path="specificSymbol" maxlength="10"/>
                                </div>
                                <div class="form-group">
                                    <form:label path="message">Zpráva pro příjemce:</form:label><form:errors class="formError" path='message' />
                                    <form:input type="text" class="form-control" path="message" maxlength="255" />
                                </div>
                            </div>
                            <div class="form-group col-12 text-center mt-sm-5">
                                <form:hidden path="id"/>
                                <button type="submit" class="btn btn-primary">Upravit šablonu</button>
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