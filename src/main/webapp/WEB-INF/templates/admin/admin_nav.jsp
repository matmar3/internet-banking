<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
    <nav>
        <div class="nav">
            <ul class="nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/index"><spring:message code="admin.nav.customerAccounts" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/admin/create-user"><spring:message code="admin.nav.createAccount" /></a>
                </li>
            </ul>
            <ul class="nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">${authorizedUser.firstName} ${authorizedUser.lastName}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/admin/profile"><spring:message code="admin.nav.accountSettings" /> <i class="fas fa-cog"></i></a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="javascript:logout()"><spring:message code="admin.nav.logOut" /> <i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>

<jsp:include page="../sections/logout_form.jsp" />