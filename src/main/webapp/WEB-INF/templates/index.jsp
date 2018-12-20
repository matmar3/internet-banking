<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>

<layout:extends name="base.jsp">
    <layout:put block="styles">
        <link rel="stylesheet" href="css/homepage.css">
    </layout:put>

    <layout:put block="content">

        <div class="container">
            <nav>
                <div class="nav">
                    <ul class="nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Půjčky</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Hypotéky</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Spoření</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Pojištění</a>
                        </li>
                    </ul>
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Internetové bankovnictví</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>

        <main id="main-trap">
            <img src="images/article.jpeg" alt="Reklama">
        </main>

        <div class="container">
            <div class="row frequent-services justify-content-between">
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center">Osobní účet zdarma</div>
                    <div class="block-section-content"></div>
                </div>
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center">Kurzovní lístek</div>
                    <div class="block-section-content"></div>
                </div>
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center">Nejčastější dotazy</div>
                    <div class="block-section-content"></div>
                </div>
            </div>
        </div>

        <div class="container-fluid services">
            <div class="container">
                <div class="row pt-3 pb-3 mb-3">
                    <div class="col-md-4">
                        <div class="services-heading">Produkty</div>
                        <ul class="services-items">
                            <li>Hypotéky</li>
                            <li>Spoření</li>
                            <li>Pojištění</li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <div class="services-heading">O nás</div>
                        <ul class="services-items">
                            <li>Kdo jsme</li>
                            <li>Pobočky</li>
                            <li>Kontakt</li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <div class="services-heading">Užitečné informace</div>
                        <ul class="services-items">
                            <li>Časté dotazy</li>
                            <li>Ochrana osobních údajů</li>
                            <li>Ceník služeb</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </layout:put>
</layout:extends>