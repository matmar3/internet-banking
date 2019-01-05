<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<layout:extends name="../base.jsp">
    <layout:put block="title" type="REPLACE">
        <title><spring:message code="ib.profile.title" /></title>
    </layout:put>

    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="ib_nav.jsp" />

        <jsp:include page="../sections/edit_profile_form.jsp" />

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>