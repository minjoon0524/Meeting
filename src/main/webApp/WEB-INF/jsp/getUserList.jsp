<%@ page import="com.obj.meeting.dto.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.client.RestTemplate" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
    <!-- 부트스트랩 CSS 추가 -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            var token = localStorage.getItem("authToken");
            if (!token) {
                window.location.href = "/login";
                return;
            }

            var apiUrl = '/user/list';
            $.ajax({
                url: apiUrl,
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function(users) {
                    var tableBody = $('#userTable tbody');
                    tableBody.empty();
                    users.forEach(function(user) {
                        var row = $('<tr></tr>');
                        row.append($('<td></td>').html('<a href="getUser.jsp?username=' + encodeURIComponent(user.username) + '">' + user.username + '</a>'));
                        row.append($('<td></td>').text(user.email));
                        row.append($('<td></td>').text(user.role));
                        tableBody.append(row);
                    });
                },
                error: function(xhr, status, error) {
                    $('#userTable').after("<p class='text-danger'>사용자 목록 불러오기 오류 : " + error + "</p>");
                }
            });
        });
    </script>
</head>
<body>
<div class="container mt-4">
    <h1>사용자 목록</h1>
    <table class='table table-striped' id="userTable">
        <thead class='thead-dark'>
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <!-- AJAX로 데이터가 채워짐 -->
        </tbody>
    </table>
</div>
<!-- 부트스트랩 JS 추가 -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
