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
                    <div class="col-12">
                        <div class="h3">Šablony plateb</div>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Název</th>
                                <th>Detail převodu</th>
                                <th>Částka</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${templates}" var="template">
                                <tr>
                                    <td>${template.templateName}</td>
                                    <td>${template.senderAccountNumber} -> ${template.receiverAccountNumber}</td>
                                    <td>${template.sentAmount}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>