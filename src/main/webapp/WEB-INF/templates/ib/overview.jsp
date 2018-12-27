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
                            <a class="nav-link" href="/ib/index">Souhrn</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="/ib/transactions">Transakční historie</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Jednorázové platby</a>
                        </li>
                    </ul>
                    <ul class="nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">${authorizedUser.username}</a>
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
                    <div class="col-lg-5 col-md-6 col-12">
                        <div class="h3">Rychlý přehled</div>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th colspan="3">Poslední transakce</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>01.09.2018</td>
                                <td>Běžný účet<br />1011029938/6210</td>
                                <td>-0,01 CZK</td>
                            </tr>
                            <tr>
                                <td>15.10.2018</td>
                                <td>Zaměstnavatel<br />1011034139/6100</td>
                                <td>26 000,00 CZK</td>
                            </tr>
                            <!-- TODO poslednich 10 zaznamu, doplnit praznymi radky -->
                            </tbody>
                        </table>
                    </div>
                    <div class="col-lg-7 col-md-6 col-12">
                        <div>
                            <div class="h3">Stav účtu</div>
                        </div>
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Účet</th>
                                <th>Blokováno</th>
                                <th>Účetní zůstatek</th>
                                <th>Disponibilní zůstatek</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Běžný účet<br />1011029938/6210</td>
                                <td>0,00 CZK</td>
                                <td>153 623,00 CZK</td>
                                <td>153 623,00 CZK</td>
                            </tr>
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