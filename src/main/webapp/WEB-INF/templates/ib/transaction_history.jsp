<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="../../css/account.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row">
                    <form id="filter-transactions-form" class="col-12 mb-sm-3">
                        <div class="form-group">
                            <div class="form-inline">
                                <div class="form-group mr-sm-3">
                                    <label for="date_from">Od:&nbsp;</label>
                                    <input type="text" class="form-control form-control-sm" id="date_from">
                                </div>
                                <div class="form-group mr-sm-3">
                                    <label for="date_to">Do:&nbsp;</label>
                                    <input type="text" class="form-control form-control-sm" id="date_to">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-sm">Vyhledat</button>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="h3">Transakční historie</div>
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>Zaúčtováno</th>
                                    <th>Typ platby</th>
                                    <th>Variabilní symbol</th>
                                    <th>Částka</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${transactions}" var="transaction">
                                    <c:choose>
                                        <c:when test="${account.accountNumber == transaction.receiverAccountNumber}">
                                            <tr>
                                                <td>${transaction.createdDate}</td>
                                                <td>Příchozí částka<br />${transaction.receiverAccountNumber} <- ${transaction.senderAccountNumber}</td>
                                                <td>${transaction.variableSymbol}</td>
                                                <td>+ ${transaction.receivedAmount}</td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td>${transaction.createdDate}</td>
                                                <td>Odchozí částka<br />${transaction.senderAccountNumber} -> ${transaction.receiverAccountNumber}</td>
                                                <td>${transaction.variableSymbol}</td>
                                                <td>- ${transaction.sentAmount}</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <nav class="col-12">
                        <ul class="pagination justify-content-center">
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>