<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="contact.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/contact.css">
        <link rel="stylesheet" href="/css/services.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="sections/public_nav.jsp" />

        <div class="container-fluid lb-content-wrapper">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 h1 text-center"><spring:message code="contact.heading" /></div>
                    <div class="col-12 text-center slogan"><spring:message code="contact.slogan" /></div>
                    <div class="col-md-6 col-12">
                        <div class="contact-section">
                            <span class="section-heading"><spring:message code="contact.infoline.section" /></span><br />
                            <div class="contact-subsection">
                                <span class="section-heading"><spring:message code="contact.infoline.label" /></span><br />
                                <spring:message code="contact.infoline.phone" /><br />
                                <spring:message code="contact.infoline.officeHours" /><br />
                                <spring:message code="contact.infoline.officeHoursEn" />
                            </div>
                            <div class="contact-subsection">
                                <span class="section-heading"><spring:message code="contact.infolineAbroad.label" /></span><br />
                                <spring:message code="contact.infolineAbroad.phone" /><br />
                                <spring:message code="contact.infolineAbroad.officeHours" /><br />
                                <spring:message code="contact.infolineAbroad.officeHoursEn" />
                            </div>
                            <div class="contact-subsection">
                                <span class="section-heading"><spring:message code="contact.cardSupport.label" /></span><br />
                                <spring:message code="contact.cardSupport.phone" /><br />
                            </div>
                            <div class="contact-subsection">
                                <spring:message code="contact.fax" />
                            </div>
                            <div class="contact-subsection">
                                <spring:message code="contact.email" />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-12">
                        <div class="contact-section">
                            <div class="contact-subsection">
                                <span class="section-heading"><spring:message code="contact.headquarters.label" /></span><br />
                                <spring:message code="contact.headquarters.address1" /><br />
                                <spring:message code="contact.headquarters.address2" /><br />
                                <spring:message code="contact.headquarters.address3" />
                            </div>
                            <div class="contact-subsection">
                                <spring:message code="contact.company.id" /><br />
                                <spring:message code="contact.company.vat" /><br />
                                <spring:message code="contact.company.boxId" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="sections/services.jsp" />

    </layout:put>

    <layout:put block="scripts">
    </layout:put>

</layout:extends>