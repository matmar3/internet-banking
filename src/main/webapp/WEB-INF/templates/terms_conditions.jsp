<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="termsAncConditions.title" /></title>
    </layout:put>

    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <div class="container-fluid">
            <div class="row lb-content-wrapper justify-content-center">
                <div class="h1">Terms and Conditions</div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">
    </layout:put>

</layout:extends>