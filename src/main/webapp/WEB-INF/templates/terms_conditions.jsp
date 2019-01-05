<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="termsAndConditions.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/services.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="sections/public_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 h1"><spring:message code="termsAndConditions.heading" /></div>
                    <div class="col-12 h2"><spring:message code="termsAndConditions.subheading" /></div>
                    <div class="col-12">
                        <p><spring:message code="termsAndConditions.section.first" /></p>
                        <p><spring:message code="termsAndConditions.section.second" /></p>
                        <p><spring:message code="termsAndConditions.section.third" /></p>
                        <p><spring:message code="termsAndConditions.section.fourth" /></p>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="sections/services.jsp" />

    </layout:put>

    <layout:put block="scripts">
    </layout:put>

</layout:extends>