package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Match {
    private int matchId;
    private String opponent; // DoiThu
    private LocalDate matchDate; // NgayThiDau
    private LocalTime matchTime; // GioThiDau
    private String stadiumName; // TenSan
    private double basePrice; // GiaVeCoBan

    public Match(int matchId, String opponent, LocalDate matchDate, LocalTime matchTime, String stadiumName, double basePrice) {
        this.matchId = matchId;
        this.opponent = opponent;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.stadiumName = stadiumName;
        this.basePrice = basePrice;
    }
    
    // Constructor mặc định và Getters/Setters
    public int getMatchId() { return matchId; }
    public String getOpponent() { return opponent; }
    public double getBasePrice() { return basePrice; }
    public LocalDate getMatchDate() { return matchDate; }
    public LocalTime getMatchTime() { return matchTime; }
    public String getStadiumName() { return stadiumName; }
    // ... (Thêm các getters/setters khác)
}