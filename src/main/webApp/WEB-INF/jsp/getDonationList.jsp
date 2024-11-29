<%@ page import="java.util.List, org.springframework.web.client.RestTemplate, java.util.Arrays, com.obj.meeting.dto.RequestDonation" %>
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

            var apiUrl = '/donation/list';
            $.ajax({
                url: apiUrl,
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                /* success: function(donations) {
                     var tableBody = $('#donationsTable tbody');
                     tableBody.empty();
                     donations.forEach(function(donations) {
                         var row = $('<tr></tr>');
                         row.append($('<td></td>').html('<a href="/getdonations?donationsId=' + encodeURIComponent(donations.donationsId) + '">' + donations.donationsId + '</a>'));
                         row.append($('<td></td>').text(donations.donationsName));
                         row.append($('<td></td>').text(donations.meetingDate));
                         row.append($('<td></td>').text(donations.meetingAddress));
                         row.append($('<td></td>').text(donations.participantCount));
                         tableBody.append(row);
                     });
                 },*/
                success: function(donations) {
                    var tableBody = $('#donationsTable tbody');
                    tableBody.empty();
                    donations.forEach(function(donations) {

                        var row = $('<tr></tr>');
                        row.append($('<td></td>').html('<a href="#" onclick="loadPage(\'/getDonation?id=' + encodeURIComponent(donations.id) + '\'); return false;">' + donations.id + '</a>'));

                        row.append($('<td></td>').text(donations.name));
                        row.append($('<td></td>').text(donations.creator));
                        row.append($('<td></td>').text(donations.description));
                        row.append($('<td></td>').text(donations.donationAmount));
                        row.append($('<td></td>').text(donations.totalAmount));
                        row.append($('<td></td>').text(donations.dueDate));
                        row.append($('<td></td>').text(donations.donorCount));
                        tableBody.append(row);
                    });
                },

                error: function (xhr, status, error) {
                    $('#donationsTable').after("<p class='text-danger'>Error fetching donations list: " + error + "</p>");
                }
            });
        });
    </script>
</head>
<body>
<div class="container mt-4">
    <h1>모임목록</h1>
    <table id="donationsTable" class="table table-striped">
        <thead class='thead-dark'>
        <tr>
            <th>기부ID</th>
            <th>기부 명</th>
            <th>기부개설자</th>
            <th>기부설명</th>
            <th>기부금액</th>
            <th>총금액</th>
            <th>기부 마감일자</th>
            <th>기부자수</th>
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