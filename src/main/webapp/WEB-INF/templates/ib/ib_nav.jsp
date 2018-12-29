<%@ page contentType="text/html; charset=UTF-8" %>

<div class="container">
    <nav>
        <div class="nav">
            <ul class="nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/ib/index">Souhrn</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/ib/transactions">Transakční historie</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Jednorázové platby</a>
                </li>
            </ul>
            <ul class="nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">${authorizedUser.username}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/ib/profile"><i class="fas fa-cog"></i> Nastavení</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="/logout">Odhlásit se <i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>