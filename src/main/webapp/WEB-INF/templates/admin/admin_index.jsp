<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <div class="container">
            <nav>
                <div class="nav">
                    <ul class="nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="/admin/index">Zákaznické účty</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/create-user">Založit účet</a>
                        </li>
                    </ul>
                    <ul class="nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">User 001</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Nastavení</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="/logout">Odhlásit se <i class="fas fa-sign-out-alt"></i></a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="h3">Přehled účtů</div>
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Jméno</th>
                                    <th>Příjmení</th>
                                    <th>E-mail</th>
                                    <th>Adresa</th>
                                    <th></th>
                                </tr>
                            </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${not empty customers}">
                                        <c:forEach items="${customers}" var="customer">
                                            <tr>
                                                <td>${customer.id}</td>
                                                <td>${customer.firstName}</td>
                                                <td>${customer.lastName}</td>
                                                <td>${customer.email}</td>
                                                <td>${customer.address}</td>
                                                <td><a href="#" class="badge badge-danger"><i class="fas fa-trash-alt"></i></a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="6">Neexistují žádné zákaznické účty.</td>
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