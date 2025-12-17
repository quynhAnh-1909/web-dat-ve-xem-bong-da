package com.hagl.dao;

import java.sql.*;
import java.util.*;
import com.hagl.model.Match;
import java.time.LocalDate;
import java.time.LocalTime;

public class MatchDAO extends BaseDAO {

    public MatchDAO() { 
        super(); 
    }

    public List<Match> getAllMatches() throws SQLException {
        List<Match> matchList = new ArrayList<>();
        
        // 1. CẬP NHẬT SQL: Thêm T.TenAnh vào câu lệnh SELECT
        String sql = "SELECT T.MaTD, T.DoiThu, T.NgayThiDau, T.GioThiDau, T.GiaVeCaoNhat, T.GiaVeThapNhat, S.TenSan, T.TenAnh "
                   + "FROM TRANDAU T JOIN SANVANDONG S ON T.MaSan = S.MaSan "
                   + "ORDER BY T.NgayThiDau DESC, T.GioThiDau DESC"; 

        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Timestamp ngayThiDauTs = rs.getTimestamp("NgayThiDau");
                Timestamp gioThiDauTs = rs.getTimestamp("GioThiDau");
                
                java.time.LocalDate ngayThiDau = (ngayThiDauTs != null) 
                                     ? ngayThiDauTs.toLocalDateTime().toLocalDate() 
                                     : null;
                
                java.time.LocalTime gioThiDau = (gioThiDauTs != null) 
                                     ? gioThiDauTs.toLocalDateTime().toLocalTime() 
                                     : null;

                Match match = new Match(
                    rs.getInt("MaTD"),
                    rs.getString("DoiThu"),
                    ngayThiDau, 
                    gioThiDau,  
                    rs.getString("TenSan"), 
                    rs.getDouble("GiaVeCaoNhat"), 
                    rs.getDouble("GiaVeThapNhat")
                );
                
                // 2. GÁN GIÁ TRỊ TÊN ẢNH: Đọc từ cột "TenAnh" và lưu vào Model
                // (Đảm bảo Class Match của bạn đã có thuộc tính opponentImageName và hàm set tương ứng)
                match.setOpponentImageName(rs.getString("TenAnh"));
                
                System.out.println("------------------------------------");
                System.out.println("DAO DEBUG: Đã đọc thành công trận đấu: " + match.getOpponent());
                System.out.println("  Ảnh đối thủ: " + match.getOpponentImageName()); // Debug tên file .jpg
                System.out.println("------------------------------------");
                
                matchList.add(match);
            }
            
            System.out.println("DAO DEBUG: Tổng số trận đấu được tìm thấy: " + matchList.size());

        } catch (SQLException e) {
            System.err.println("DAO ERROR: Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        }
        return matchList;
    }
    public List<Match> getMatchesByMonth(int month, int year) throws SQLException {
        List<Match> matchList = new ArrayList<>();
        // Sử dụng hàm MONTH() và YEAR() của SQL Server để lọc
        String sql = "SELECT T.MaTD, T.DoiThu, T.NgayThiDau, T.GioThiDau, T.GiaVeCaoNhat, T.GiaVeThapNhat, S.TenSan, T.TenAnh "
                   + "FROM TRANDAU T JOIN SANVANDONG S ON T.MaSan = S.MaSan "
                   + "WHERE MONTH(T.NgayThiDau) = ? AND YEAR(T.NgayThiDau) = ? "
                   + "ORDER BY T.NgayThiDau ASC";

        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Match match = new Match(/* gán dữ liệu như cũ */);
                    match.setOpponentImageName(rs.getString("TenAnh"));
                    matchList.add(match);
                }
            }
        }
        return matchList;
    }
}