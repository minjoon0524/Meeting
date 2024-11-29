<%@ page import="java.util.List, org.springframework.web.client.RestTemplate, java.util.Arrays, com.obj.meeting.dto.Group" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 목록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            var token = localStorage.getItem("authToken");
            if (!token) {
                alert("No token found. Redirecting to login page.");
                window.location.href = "/login";
                return;
            }

            var apiUrl = '/group/list';
            $.ajax({
                url: apiUrl,
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                /* success: function(groups) {
                     var tableBody = $('#groupTable tbody');
                     tableBody.empty();
                     groups.forEach(function(group) {
                         var row = $('<tr></tr>');
                         row.append($('<td></td>').html('<a href="/getGroup?groupId=' + encodeURIComponent(group.groupId) + '">' + group.groupId + '</a>'));
                         row.append($('<td></td>').text(group.groupName));
                         row.append($('<td></td>').text(group.meetingDate));
                         row.append($('<td></td>').text(group.meetingAddress));
                         row.append($('<td></td>').text(group.participantCount));
                         tableBody.append(row);
                     });
                 },*/
                success: function(groups) {
                    var tableBody = $('#groupTable tbody');
                    tableBody.empty();
                    groups.forEach(function(group) {
                        var row = $('<tr></tr>');
                        row.append($('<td></td>').html('<a href="#" onclick="loadPage(\'/getGroup?groupId=' + encodeURIComponent(group.groupId) + '\'); return false;">' + group.groupId + '</a>'));
                        row.append($('<td></td>').text(group.groupName));
                        row.append($('<td></td>').text(group.meetingDate));
                        row.append($('<td></td>').text(group.meetingAddress));
                        row.append($('<td></td>').text(group.participantCount));
                        tableBody.append(row);
                    });
                },

                error: function(xhr, status, error) {
                    $('#groupTable').after("<p class='text-danger'>Error fetching group list: " + error + "</p>");
                }
            });
        });
    </script>
</head>
<body>
<div class="container mt-4">
    <h1>모임목록</h1>
    <table id="groupTable" class="table table-striped">
        <thead class='thead-dark'>
        <tr>
            <th>모임 ID</th>
            <th>모임 명</th>
            <th>모임 날짜</th>
            <th>모임 장소</th>
            <th>참여자 수</th>
        </tr>
        </thead>
        <tbody>
        <!-- 모임 목록 자동 삽입 -->
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>