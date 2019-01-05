<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="homepage.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/homepage.css">
        <link rel="stylesheet" href="/css/services.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="sections/public_nav.jsp" />

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
                    <div class="block-section-content text-center">
                        <c:forEach items="${exchangeRates}" var="rate">
                            <span class="exchangeRateLabel">
                                    ${rate.key}
                            </span>
                            <span class="exchangeRate">
                                    ${rate.value}
                            </span>
                            <br />
                        </c:forEach>
                    </div>
                </div>
                <div class="custom-col-md-35 col-12">
                    <div class="block-section-title text-center"><spring:message code="homepage.services.faq" /></div>
                    <div class="block-section-content"><spring:message code="homepage.services.faq.content" /></div>
                </div>
            </div>
        </div>

        <div class="container-fluid bank-info">
            <div class="container">
                <div class="row pt-3 pb-3 mb-3">
                    <div class="col-12">
                        <p><spring:message code="homepage.info.first" /></p>
                        <p><spring:message code="homepage.info.second" /></p>
                        <p><spring:message code="homepage.info.third" /></p>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="sections/services.jsp" />

    </layout:put>
</layout:extends>