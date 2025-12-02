<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Select Seat</title>
    <style>
        /* ... (CSS cho sơ đồ và ghế không thay đổi) ... */
    </style>
</head>
<body>
    <h2>Booking for Match: ${match.opponent}</h2>
    <p>Time: ${match.matchDate} at ${match.matchTime}</p>
    <p>Stadium: ${match.stadiumName}</p>
    
    <h3>1. Select Area</h3>
    <div id="stadium-map">
        <a id="A1" class="area-link" href="matchDetail?matchId=${match.matchId}&area=A1">A1 (Premium)</a>
        <a id="C" class="area-link" href="matchDetail?matchId=${match.matchId}&area=C">C (Standard)</a>
    </div>

    <c:if test="${not empty selectedArea}">
        <h3>2. Select Seat in Area ${selectedArea}</h3>
        <p>Final Ticket Price: <b><span id="displayFinalPrice">${giaVeFinal}</span> VND</b></p>

        <form action="booking" method="POST" id="bookingForm">
            <input type="hidden" name="matchId" value="${match.matchId}">
            <input type="hidden" name="finalPrice" id="inputFinalPrice" value="${giaVeFinal}">
            <input type="hidden" name="seatLocation" id="selectedSeatInput">

            <div class="seat-grid">
                <c:forEach begin="1" end="10" var="i">
                    <c:set var="seatId" value="${selectedArea}_${i}"/>
                    <c:set var="isBooked" value="false"/>
                    
                    <c:forEach var="bookedSeat" items="${bookedSeats}">
                        <c:if test="${bookedSeat eq seatId}">
                            <c:set var="isBooked" value="true"/>
                        </c:if>
                    </c:forEach>

                    <div class="seat <c:if test="${isBooked}">booked</c:if><c:if test="${!isBooked}">available</c:if>" 
                         data-seat-id="${seatId}" 
                         onclick="if (!this.classList.contains('booked')) selectSeat(this)">
                         Seat ${i}
                    </div>
                </c:forEach>
            </div>
            
            <h3 style="margin-top: 20px;">Selected Seat: <span id="displaySelectedSeat">None</span></h3>
            <button type="submit" id="bookBtn" disabled>Proceed to VNPAY Payment</button>
        </form>
    </c:if>

    <script>
        function selectSeat(element) {
            document.querySelectorAll('.seat').forEach(seat => seat.classList.remove('selected'));
            element.classList.add('selected');

            const seatId = element.getAttribute('data-seat-id');
            document.getElementById('displaySelectedSeat').textContent = seatId;
            document.getElementById('selectedSeatInput').value = seatId;
            document.getElementById('bookBtn').disabled = false;
        }
    </script>
</body>
</html>