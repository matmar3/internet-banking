<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7 col-md-6 col-12">
                        <div class="h3">Přehled účtů</div>
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>Číslo účtu</th>
                                    <th>Číslo platební karty</th>
                                    <th>Účetní zůstatek</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${accounts}" var="account">
                                    <tr>
                                        <td>${account.accountNumber}</td>
                                        <td>${account.cardNumber}</td>
                                        <td>${account.balance}</td>
                                        <td>Zde bude odkaz</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-lg-5 col-md-6 col-12">
                        <form:form class="col-12 align-self-center" action="/ib/create-account" method="post" modelAttribute="newAccount">
                            <div class="form-row">
                                <div class="form-group col-12">
                                    <div class="h3">Založit nový účet</div>
                                    <div class="form-group">
                                        <form:label path="currency">Měna: *</form:label><form:errors class="formError" path='currency' />
                                        <form:select class="form-control" path="currency" items="${currencies}" required="required" />
                                    </div>
                                </div>
                                <div class="form-group col-12 text-center mt-sm-5">
                                    <input type="submit" class="btn btn-primary" value="Založit účet"/>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>