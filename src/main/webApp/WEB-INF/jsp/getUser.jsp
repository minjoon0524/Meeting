<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Information</title>
    <!-- 부트스트랩 CSS 추가 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h1 class='mb-3'>User Information</h1>
    <div id="userInfo" class="card" style="display: none;">
        <div class="card-body"></div>
    </div>
    <p id="error" class="text-danger" style="display: none;"></p>
</div>

<script>
    $(document).ready(function() {
        var token = localStorage.getItem("authToken");
        var username = new URLSearchParams(window.location.search).get('username');
        var apiUrl = 'http://localhost:8080/user/get/' + username;

        if (!token) {
            window.location.href = "/login";
            return;
        }

        $.ajax({
            url: apiUrl,
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(user) {
                var cardBody = $('#userInfo .card-body');
                $('#userInfo').show();
                cardBody.append("<h5 class='card-title'>ID: " + user.username + "</h5>");
                cardBody.append("<p class='card-text'>Username: " + user.username + "</p>");
                cardBody.append("<p class='card-text'>Email: " + user.email + "</p>");
                cardBody.append("<p class='card-text'>Role: " + user.role + "</p>");
            },
            error: function(xhr, status, error) {
                $('#error').text(error).show();
            }
        });
    });
</script>
<!-- 부트스트랩 JS 추가 -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
