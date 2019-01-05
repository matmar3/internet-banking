<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="errorPage.400.title" /></title>
    </layout:put>

    <layout:put block="styles">
        <link rel="stylesheet" href="/css/homepage.css">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="../sections/public_nav.jsp" />

        <div class="container-fluid error-wrapper lb-content-wrapper">
            <div class="container h-100">
                <div class="row h-100 align-items-center justify-content-center">
                    <div class="col-12 text-center">
                        <div class="h1">
                            <spring:message code="errorPage.400.status" />
                        </div>
                        <spring:message code="errorPage.400.description" />
                    </div>
                </div>
            </div>
        </div>

    </layout:put>
</layout:extends>