<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
    <nav>
        <div class="nav public-nav">
            <ul class="nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#"><spring:message code="homepage.nav.loans" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><spring:message code="homepage.nav.mortgage" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><spring:message code="homepage.nav.insurance" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/contact"><spring:message code="homepage.nav.contact" /></a>
                </li>
            </ul>
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="/login"><spring:message code="homepage.nav.ib" /></a>
                </li>
            </ul>
        </div>
    </nav>
</div>