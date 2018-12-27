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
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">User 001</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Nastavení</a>
                                <a class="dropdown-item" href="#"><i class="fas fa-question"></i> Nápověda</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Odhlásit se <i class="fas fa-sign-out-alt"></i></a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>

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
                            <tr>
                                <td>01.09.2018</td>
                                <td>Příchozí částka<br />Běžný účet <- Jiný účet</td>
                                <td></td>
                                <td>+3 000,00 CZK</td>
                            </tr>
                            <tr>
                                <td>01.09.2018</td>
                                <td>Příchozí částka<br />Běžný účet <- Jiný účet</td>
                                <td></td>
                                <td>+3 000,00 CZK</td>
                            </tr>
                            <tr>
                                <td>01.09.2018</td>
                                <td>Příchozí částka<br />Běžný účet <- Jiný účet</td>
                                <td></td>
                                <td>+3 000,00 CZK</td>
                            </tr>
                            <tr>
                                <td>01.09.2018</td>
                                <td>Příchozí částka<br />Běžný účet <- Jiný účet</td>
                                <td></td>
                                <td>+3 000,00 CZK</td>
                            </tr>
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