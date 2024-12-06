<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Login</h2>
    <form id="loginForm" method="post" class="mt-3">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
    <p></p>
    <a href="/oauth2/authorization/naver">네이버</a>
    <a href="/oauth2/authorization/kakao">카카오 로그인</a>
</div>

<footer style="position: fixed; bottom: 0; width: 100%; background-color: #f8f9fa; text-align: center; padding: 10px 0;">
    <p>© 2024 C.J.Kim (Dept.of Computer & System Engineering). All rights reserved.</p>
</footer>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password)
        })
            .then(response => {
                const authToken = response.headers.get('Authorization');
                // alert('login 후 토큰 발급 : '+authToken);
                if (response.ok && authToken) {
                    localStorage.setItem('authToken', authToken.split(' ')[1]);
                    localStorage.setItem('username', username);
                    window.location.href = '/menu';
                } else {
                    throw new Error('로그인 실패 : ' + response.status);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('로그인 실패 : ' + error.message);
            });
    });
</script>
</body>
</html>
