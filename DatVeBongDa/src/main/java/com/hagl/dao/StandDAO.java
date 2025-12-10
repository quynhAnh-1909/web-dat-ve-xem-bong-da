package com.hagl.dao;

import java.sql.*;
import java.util.*;

public class StandDAO extends BaseDAO {

    public StandDAO() { super(); }

    /**
     * Lấy giá vé cố định (GiaVeCoDinh) trực tiếp từ bảng KHUVUC
     * @param standName Tên khu vực (VD: A1, B, C)
     * @return Giá vé cố định của khu vực đó
     */
    public double getFinalPrice(String standName) throws SQLException {
        String sql = "SELECT GiaVeCoDinh FROM KHUVUC WHERE TenKhu = ?";
        double finalPrice = 0;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, standName); 
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    finalPrice = rs.getDouble("GiaVeCoDinh");
                }
            }
        }
        return finalPrice;
    }
    
    /**
     * Lấy danh sách vị trí ghế đã được đặt thành công cho một trận đấu và khu vực cụ thể
     * @param matchId Mã trận đấu
     * @param standName Tên khu vực (VD: A1)
     * @return List<String> chứa các vị trí ghế (VD: "A1_15", "A1_16")
     */
    public List<String> getBookedSeats(int matchId, String standName) throws SQLException {
        List<String> bookedSeats = new ArrayList<>();
        // Tìm kiếm các vé đã đặt thành công và vị trí ghế nằm trong khu vực được chọn
        String sql = "SELECT ViTriNgoi FROM VE WHERE MaTD = ? AND ViTriNgoi LIKE ? AND TrangThai = 'Success'";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, matchId);
            ps.setString(2, standName + "_%"); // Ví dụ: "A1_%"
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookedSeats.add(rs.getString("ViTriNgoi"));
                }
            }
        }
        return bookedSeats;
    }
}