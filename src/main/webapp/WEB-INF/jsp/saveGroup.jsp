<%@ page import="com.obj.meeting.dto.User" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 등록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD9yUzGTNYInF7AuwTb1K08WrCL_izRjEM&callback=initMap&libraries=places" async defer></script>
    <script>
        let map;
        let marker;
        let geocoder;

        function initMap() {
            const inha = {lat: 37.4480158, lng: 126.6575041}; // 인하공전 위치 (37.4480158, 126.6575041)
            map = new google.maps.Map(document.getElementById('map'), {
                center: inha,
                zoom: 15
            });
            marker = new google.maps.Marker({
                position: inha,
                map: map,
                draggable: true 
            });
            geocoder = new google.maps.Geocoder();
            
            map.addListener('click', function(e) {
                geocodeLatLng(e.latLng);
            });
        }

        function geocodeLatLng(latlng) {
            geocoder.geocode({'location': latlng}, function(results, status) {
                if (status === 'OK') {
                    if (results[0]) {
                        map.setCenter(results[0].geometry.location);
                        marker.setPosition(results[0].geometry.location);
                        $('#meetingAddress').val(results[0].formatted_address);
                    } else {
                        window.alert('No results found');
                    }
                } else {
                    window.alert('Geocoder failed due to: ' + status);
                }
            });
        }

        $(document).ready(function() {
            $('#meetingAddress').on('input', function() {
                var address = $(this).val();
                if(address.length > 5) {
                    geocodeAddress(address);
                }
            });

            function geocodeAddress(address) {
                geocoder.geocode({'address': address}, function(results, status) {
                    if (status === 'OK') {
                        map.setCenter(results[0].geometry.location);
                        marker.setPosition(results[0].geometry.location);
                    } else {
                        alert('Geocode was not successful for the following reason: ' + status);
                    }
                });
            }

            $('#groupForm').on('submit', function(e) {
                e.preventDefault();
                var formData = new FormData(this);
                $.ajax({
                    url: '/group/save',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('authToken')
                    },
                    success: function(response) {
                        alert('모임이 성공적으로 등록되었습니다.');
                    },
                    error: function(xhr, status, error) {
                        alert('모임 등록에 실패했습니다: ' + error);
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container mt-4">
    <h2>모임 등록</h2>
    <form id="groupForm" method="post" class="mt-3" enctype="multipart/form-data">
        <div class="form-group">
            <label for="groupName">모임 명:</label>
            <input type="text" class="form-control" id="groupName" name="groupName" required>
        </div>
        <div class="form-group">
            <%
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = ((UserDetails)principal).getUsername();
            %>
            <label for="groupManagerId">모임 방장:</label>
            <input type="text" class="form-control" id="groupManagerId" name="groupManagerId" value="<%=username%>" required readonly>
        </div>
        <div class="form-group">
            <label for="groupDescription">모임 설명:</label>
            <input type="text" class="form-control" id="groupDescription" name="groupDescription" required>
        </div>
        <div class="form-group">
            <label for="groupImage">모임 이미지:</label>
            <input type="file" class="form-control-file" id="groupImage" name="groupImage" required>
        </div>
        <div class="form-group">
            <label for="meetingDate">모임 날짜:</label>
            <input type="date" class="form-control" id="meetingDate" name="meetingDate" required>
        </div>
        <div class="form-group">
            <label for="meetingAddress">모임 장소:</label>
            <input type="text" class="form-control" id="meetingAddress" name="meetingAddress" required>
        </div>
        <div id="map" style="height: 400px;"></div>
        <button type="submit" class="btn btn-primary">모임 등록</button>
    </form>
</div>
</body>
</html>
