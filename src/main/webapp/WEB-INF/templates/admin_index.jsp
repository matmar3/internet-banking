<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="base.jsp">
    <layout:put block="styles">
    </layout:put>

    <layout:put block="content">

        <div class="container-fluid">
            <c:if test="${not empty errorMessage}" >
                <div class="row justify-content-center">
                    <div class="alert alert-warning" role="alert">
                            ${errorMessage}
                    </div>
                </div>
            </c:if>
            <div class="row">
                <div class="col-md-9 col-12 order-md-1 order-2">
                    zde bude tabulka zakazniku a pod ni administratoru
                </div>
                <div class="col-md-3 col-12 order-md-2 order-1">
                    zde budou polozky menu
                </div>
            </div>
        </div>

    </layout:put>

    <layout:put block="scripts">

    </layout:put>
</layout:extends>