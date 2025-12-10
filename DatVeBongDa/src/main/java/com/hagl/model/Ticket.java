package com.hagl.model;

import java.time.LocalDateTime;

public class Ticket {
    private int ticketId;
    private int matchId;
    private int userId;
    private String seatLocation; // Vị trí ngồi (VD: A1_15)
    private double finalPrice;
    private String status; // Pending, Success, Failed
    private LocalDateTime bookingTime;

    public Ticket() {}

    public Ticket(int matchId, int userId, String seatLocation, double finalPrice, String status) {
        this.matchId = matchId;
        this.userId = userId;
        this.seatLocation = seatLocation;
        this.finalPrice = finalPrice;
        this.status = status;
    }
    
    // Getters and Setters (Chỉ cung cấp getters chính)
    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    
    public int getMatchId() { return matchId; }
    public int getUserId() { return userId; }
    public String getSeatLocation() { return seatLocation; }
    public double getFinalPrice() { return finalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // ... (Thêm setters/getters khác nếu cần)
}