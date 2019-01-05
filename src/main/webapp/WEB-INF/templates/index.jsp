<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="homepage.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/homepage.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="public_nav.jsp" />

        <main id="main-trap">
            <spring:message code="homepage.trap.alt" var="adLabel" />
            <img src="/images/article.jpeg" alt="${adLabel}">
        </main>

        <div class="container">
            <div class="row frequent-services justify-content-between">
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center"><spring:message code="homepage.services.freeAccount" /></div>
                    <div class="block-section-content"><spring:message code="homepage.services.freeAccount.content" /></div>
                </div>
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center"><spring:message code="homepage.services.rates" /></div>
                    <div class="block-section-content"><!-- TODO vygenerovat nekolik kurzu --></div>
                </div>
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center"><spring:message code="homepage.services.faq" /></div>
                    <div class="block-section-content"><spring:message code="homepage.services.faq.content" /></div>
                </div>
            </div>
        </div>

        <div class="container-fluid services">
            <div class="container">
                <div class="row pt-3 pb-3 mb-3">
                    <div class="col-md-4">
                        <div class="services-heading"><spring:message code="homepage.services.products" /></div>
                        <ul class="services-items">
                            <li><spring:message code="homepage.nav.loans" /></li>
                            <li><spring:message code="homepage.nav.mortgage" /></li>
                            <li><spring:message code="homepage.nav.insurance" /></li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <div class="services-heading"><spring:message code="homepage.services.about" /></div>
                        <ul class="services-items">
                            <li><spring:message code="homepage.services.about" /></li>
                            <li><spring:message code="homepage.nav.contact" /></li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <div class="services-heading"><spring:message code="homepage.services.info" /></div>
                        <ul class="services-items">
                            <li><spring:message code="homepage.services.faq" /></li>
                            <li><spring:message code="homepage.nav.pricing" /></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </layout:put>
</layout:extends>