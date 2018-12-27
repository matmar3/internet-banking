<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="../base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <jsp:include page="admin_nav.jsp" />

        <jsp:include page="../edit_profile_form.jsp" />

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>