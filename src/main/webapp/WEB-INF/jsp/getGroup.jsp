<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 정보</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            var groupId = new URLSearchParams(window.location.search).get('groupId');
            var groupListUrl = "/group/get/" + groupId;
            var participantsUrl = "/group/get/" + groupId + "/participants";
            var joinUrl = "/group/join/" + groupId;
            var token = localStorage.getItem("authToken");

            function fetchGroupDetails() {
                $.ajax({
                    url: groupListUrl,
                    type: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    },
                    success: function(group) {

                        var groupHtml = "<h1 class='mb-3'>모임정보</h1>" +
                            "<div class='card'>" +
                            "<div class='card-body'>" +
                            "<h5 class='card-title'>모임 ID: " + group.groupId + "</h5>" +
                            "<p class='card-text'>모임 명: " + group.groupName + "</p>" +
                            "<p class='card-text'>모임 방장: " + group.groupManagerId + "</p>" +
                            "<p class='card-text'>모임 설명: " + group.groupDescription + "</p>" +
                            "<p class='card-text'>모임 이미지: <img src="+group.groupImage+" style='width:300px; height :auto;'></p>" +
                            "<p class='card-text'>모임 날짜: " + group.meetingDate + "</p>" +
                            "<p class='card-text'>모임 장소: " + group.meetingAddress + "</p>" +
                            "</div></div>";
                        $('.container').html(groupHtml);
                        joinGroup();           // 모임참여
                        fetchParticipants();   // 모임참여자 리스트
                    },
                    error: function() {
                        $('.container').html("<p class='text-danger'>모임정보 불러오기 오류</p>");
                    }
                });
            }

            // 모임 참여자 리스트
            function fetchParticipants() {
                $.ajax({
                    url: participantsUrl,
                    type: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    },
                    success: function(participants) {
                        var participantsDiv = $('#participantsDiv');
                        if (participantsDiv.length === 0) {
                            participantsDiv = $("<div id='participantsDiv'><h2 class='mt-4'>모임 참여자</h2><ul class='list-group'></ul></div>");
                            $('.container').append(participantsDiv);
                        } else {
                            participantsDiv.find('.list-group').empty();
                        }

                        var participantsList = participantsDiv.find('.list-group');
                        participants.forEach(function(participant) {
                            participantsList.append("<li class='list-group-item'>" + participant.username + " (" + participant.email + ")</li>");
                        });
                    },
                    error: function() {
                        var participantsDiv = $('#participantsDiv');
                        if (participantsDiv.length === 0) {
                            participantsDiv = $("<div id='participantsDiv'><p class='text-danger'>모임참여자 불러오기 오류</p></div>");
                            $('.container').append(participantsDiv);
                        } else {
                            participantsDiv.html("<p class='text-danger'>모임참여자 불러오기 오류</p>");
                        }
                    }
                });
            }

            // 모임 참여
            function joinGroup() {
                var joinFormHtml = "<br><form id='joinGroupForm'>" +
                    "<button type='submit' class='btn btn-primary'>모임참여하기</button></form>";
                $('.container').append(joinFormHtml);

                $('#joinGroupForm').on('submit', function(e) {
                    e.preventDefault();

                    $.ajax({
                        url: joinUrl,
                        type: 'POST',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        },
                        data: { username: localStorage.getItem('username') },
                        success: function() {
                            alert('성공적으로 모임에 참여했습니다.');
                            fetchParticipants(); // 참여자 목록 다시 불러오기
                        },
                        error: function(xhr, status, error) {
                            alert('모임 참여에 실패했습니다: ' + xhr.responseText); 
                        }
                    });
                });
            }

            fetchGroupDetails();
        });
    </script>
</head>
<body>
<div class="container mt-5">
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

