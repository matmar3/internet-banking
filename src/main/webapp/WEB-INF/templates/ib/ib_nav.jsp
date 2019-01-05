<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
    <nav>
        <div class="nav">
            <ul class="nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/ib/index"><spring:message code="ib.nav.accountsOverview" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ib/create-transaction"><spring:message code="ib.nav.payments" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ib/templates"><spring:message code="ib.nav.paymentTemplates" /></a>
                </li>
            </ul>
            <ul class="nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">${authorizedUser.firstName} ${authorizedUser.lastName}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/ib/profile"><spring:message code="ib.nav.accountSettings" /> <i class="fas fa-cog"></i></a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="javascript:logout()"><spring:message code="ib.nav.logOut" /> <i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>

<jsp:include page="../sections/logout_form.jsp" />