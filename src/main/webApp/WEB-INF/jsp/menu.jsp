<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모임서비스 메뉴</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
        }
        #contentArea {
            flex: 1 0 auto;
            overflow-y: auto;
            padding: 20px;
            padding-bottom: 100px; 
        }
        footer {
            flex-shrink: 0;
            width: 100%;
            background-color: #f8f9fa;
            text-align: center;
            padding: 10px 0;
            margin-top: 20px; 
        }
    </style>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">메뉴</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="/getUserList" onclick="loadPage('/getUserList'); return false;">사용자 리스트</a></li>
            <li class="nav-item"><a class="nav-link" href="/saveUser" onclick="loadPage('/saveUser'); return false;">사용자 등록</a></li>
            <li class="nav-item"><a class="nav-link" href="/saveDonation" onclick="loadPage('/saveDonation'); return false;">기부 등록</a></li>
            <li class="nav-item"><a class="nav-link" href="/getDonationList" onclick="loadPage('/getDonationList'); return false;">모임 리스트</a></li>
            <li class="nav-item"><a class="nav-link" href="/logout" onclick="logout(); return false;">로그아웃</a></li>
        </ul>
    </div>
</nav>

<div id="contentArea" style="padding: 20px;"></div>

<footer>
    <p>© 2024 C.J.Kim (Dept.of Computer & System Engineering). All rights reserved.</p>
</footer>

<script>
    function getCookie(cookieName) {
        const name = cookieName + "=";
        const decodedCookie = decodeURIComponent(document.cookie);
        const ca = decodedCookie.split(';');
        for(let i = 0; i < ca.length; i++) {
            let c = ca[i].trim();
            if (c.indexOf(name) == 0) {   
                return c.substring(name.length);
            }
        }
        return "";
    }


    function loadPage(url) {
        history.pushState(null, '', url);

        var token = getCookie('Authorization');

        if(token == null || token === ''){
            token = localStorage.getItem('authToken');
            // alert('일반 로그인 토큰 : '+token);
        }else {
            localStorage.setItem('authToken', token);
            // alert('OAuth2 로그인 토큰 : '+token);
        }

        //var token = localStorage.getItem('authToken');

        $.ajax({
            url: url,
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(response) {
                $('#contentArea').html(response);
            },
            error: function(xhr, status, error) {
                if (xhr.status === 403) { // 접근 거부
                    window.location.href = '/forbidden';
                } else {
                    $('#contentArea').html('<p>서비스 호출 오류: ' + error + '</p>');
                }
            }
        });
    }
    function logout() {
        document.cookie = "Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        window.location.href = '/login';
    }

</script>
</body>
</html>
