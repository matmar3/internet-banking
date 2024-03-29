<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="ib.account.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/account.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row mb-3">
                    <div class="col-12">
                        <div class="h3"><spring:message code="ib.account.accountInfo.label" /></div>
                        <div class="row">
                            <dt class="col-md-2"><spring:message code="ib.account.accountInfo.accountNumber" /></dt>
                            <dd class="col-md-10">${account.accountNumber}</dd>
                            <dt class="col-md-2"><spring:message code="ib.account.accountInfo.cardNumber" /></dt>
                            <dd class="col-md-10">${account.cardNumber}</dd>
                            <dt class="col-md-2"><spring:message code="ib.account.accountInfo.accountBalance" /></dt>
                            <dd class="col-md-10">${account.formatedBalance}</dd>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="h3"><spring:message code="ib.account.table.label" /></div>
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th><spring:message code="ib.account.table.createdOn" /></th>
                                    <th><spring:message code="ib.account.table.paymentType" /></th>
                                    <th><spring:message code="ib.account.table.amount" /></th>
                                    <th><spring:message code="ib.account.table.message" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty transactions}">
                                        <c:forEach items="${transactions}" var="transaction">
                                            <c:choose>
                                                <c:when test="${account.accountNumber == transaction.receiverAccountNumber}">
                                                    <tr>
                                                        <td>${transaction.createdDate}</td>
                                                        <td><spring:message code="ib.account.table.paymentType.incoming" /><br />${transaction.receiverAccountNumber} <- ${transaction.senderAccountNumber}</td>
                                                        <td class="transaction-profit">+ ${transaction.receivedAmount}</td>
                                                        <td>${transaction.message}</td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td>${transaction.createdDate}</td>
                                                        <td><spring:message code="ib.account.table.paymentType.outgoing" /><br />${transaction.senderAccountNumber} -> ${transaction.receiverAccountNumber}</td>
                                                        <td class="transaction-cost">- ${transaction.sentAmount}</td>
                                                        <td>${transaction.message}</td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="4"><spring:message code="ib.account.table.noData" /></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
                <c:if test="${not empty transactions}">
                    <div class="row">
                        <spring:message code="pagination.next" var="paginationNextLabel" />
                        <spring:message code="pagination.previous" var="paginationPreviousLabel" />
                        <nav class="col-12">
                            <ul class="pagination justify-content-center">
                                <li class="page-item">
                                    <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.previousOrFirst().pageNumber}&size=${pageRequest.pageSize}" aria-label="${paginationPreviousLabel}">
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">${paginationPreviousLabel}</span>
                                    </a>
                                </li>
                                <c:forEach begin="${pageRequest.first().pageNumber}" end="${totalPages - 1}" var="position">
                                    <li class="page-item ${pageRequest.pageNumber == position ? 'active' : ''}">
                                        <a class="page-link" href="/ib/account/${account.id}?page=${position}&size=${pageRequest.pageSize}">${position + 1}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pageRequest.next().pageNumber <= (totalPages - 1)}">
                                    <li class="page-item">
                                        <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.next().pageNumber}&size=${pageRequest.pageSize}" aria-label="${paginationNextLabel}">
                                            <span aria-hidden="true">&raquo;</span>
                                            <span class="sr-only">${paginationNextLabel}</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <ul class="pagination justify-content-center">
                                <li class="page-item ${pageRequest.pageSize == 10 ? 'active' : ''}">
                                    <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.pageNumber}&size=10">10</a>
                                </li>
                                <li class="page-item ${pageRequest.pageSize == 20 ? 'active' : ''}">
                                    <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.pageNumber}&size=20">20</a>
                                </li>
                                <li class="page-item ${pageRequest.pageSize == 50 ? 'active' : ''}">
                                    <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.pageNumber}&size=50">50</a>
                                </li>
                                <li class="page-item ${pageRequest.pageSize == 100 ? 'active' : ''}">
                                    <a class="page-link" href="/ib/account/${account.id}?page=${pageRequest.pageNumber}&size=100">100</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>