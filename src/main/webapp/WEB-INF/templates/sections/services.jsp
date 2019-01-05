<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container-fluid services">
    <div class="container">
        <div class="row pt-3 pb-3 mb-3">
            <div class="col-md-4">
                <div class="services-heading"><spring:message code="homepage.services.products" /></div>
                <ul class="services-items">
                    <li><a href="#"><spring:message code="homepage.nav.loans" /></a></li>
                    <li><a href="#"><spring:message code="homepage.nav.mortgage" /></a></li>
                    <li><a href="#"><spring:message code="homepage.nav.insurance" /></a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <div class="services-heading"><spring:message code="homepage.services.about" /></div>
                <ul class="services-items">
                    <li><a href="#"><spring:message code="homepage.services.about" /></a></li>
                    <li><a href="/contact"><spring:message code="homepage.nav.contact" /></a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <div class="services-heading"><spring:message code="homepage.services.info" /></div>
                <ul class="services-items">
                    <li><a href="#"><spring:message code="homepage.services.faq" /></a></li>
                    <li><a href="/terms-and-conditions"><spring:message code="homepage.services.termsAndConditions" /></a></li>
                    <li><a href="#"><spring:message code="homepage.nav.pricing" /></a></li>
                </ul>
            </div>
        </div>
    </div>
</div>