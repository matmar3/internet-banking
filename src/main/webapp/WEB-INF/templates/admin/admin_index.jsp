<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="admin_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="h3"><spring:message code="admin.index.overviewLabel" /></div>
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th><spring:message code="admin.index.table.id" /></th>
                                    <th><spring:message code="admin.index.table.firstName" /></th>
                                    <th><spring:message code="admin.index.table.lastName" /></th>
                                    <th><spring:message code="admin.index.table.email" /></th>
                                    <th><spring:message code="admin.index.table.address" /></th>
                                    <th></th>
                                </tr>
                            </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty customers}">
                                        <spring:message code="admin.index.table.removeTooltip" var="removeTooltip" />
                                        <c:forEach items="${customers}" var="customer">
                                            <tr>
                                                <td>${customer.id}</td>
                                                <td>${customer.firstName}</td>
                                                <td>${customer.lastName}</td>
                                                <td>${customer.email}</td>
                                                <td>${customer.address}</td>
                                                <td>
                                                    <a href="remove/user/${customer.id}" class="badge badge-danger" data-toggle="tooltip" data-placement="right" title="${removeTooltip}">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="6"><spring:message code="admin.index.table.noData" /></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>