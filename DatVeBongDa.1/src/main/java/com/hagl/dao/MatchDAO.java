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
        
        // Câu truy vấn lấy tất cả dữ liệu (không có WHERE)
        String sql = "SELECT T.MaTD, T.DoiThu, T.NgayThiDau, T.GioThiDau, T.GiaVeCaoNhat, T.GiaVeThapNhat, S.TenSan "
                   + "FROM TRANDAU T JOIN SANVANDONG S ON T.MaSan = S.MaSan "
                   + "ORDER BY T.NgayThiDau DESC, T.GioThiDau DESC"; 

        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            // Bắt đầu đọc dữ liệu từ ResultSet
            while (rs.next()) {
                
                // XỬ LÝ DATE/TIME AN TOÀN
                Timestamp ngayThiDauTs = rs.getTimestamp("NgayThiDau");
                Timestamp gioThiDauTs = rs.getTimestamp("GioThiDau");
                
                java.time.LocalDate ngayThiDau = (ngayThiDauTs != null) 
                                     ? ngayThiDauTs.toLocalDateTime().toLocalDate() 
                                     : null;
                
                java.time.LocalTime gioThiDau = (gioThiDauTs != null) 
                                     ? gioThiDauTs.toLocalDateTime().toLocalTime() 
                                     : null;

                // KHỞI TẠO VÀ GÁN GIÁ TRỊ VÀO ĐỐI TƯỢNG MATCH
                Match match = new Match(
                    rs.getInt("MaTD"),
                    rs.getString("DoiThu"),
                    ngayThiDau, 
                    gioThiDau,  
                    rs.getString("TenSan"), 
                    rs.getDouble("GiaVeCaoNhat"), 
                    rs.getDouble("GiaVeThapNhat")
                );
                
                // =======================================================
                // CÁC CÂU LỆNH IN (System.out.println) RA CONSOLE ECLIPSE
                // =======================================================
                System.out.println("------------------------------------");
                System.out.println("DAO DEBUG: Đã đọc thành công một trận đấu:");
                System.out.println("  MaTD: " + match.getMatchId());
                System.out.println("  Đối Thủ: " + match.getOpponent());
                System.out.println("  Ngày/Giờ: " + ngayThiDau + " lúc " + gioThiDau);
                System.out.println("  Sân: " + match.getStadiumName());
                System.out.println("------------------------------------");
                
                matchList.add(match);
            }
            
            System.out.println("DAO DEBUG: Tổng số trận đấu được tìm thấy: " + matchList.size());

        } catch (SQLException e) {
            // Báo lỗi CSDL (Mapping hoặc Truy vấn) ra Console
            System.err.println("DAO ERROR: Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        }
        return matchList;
    }
}