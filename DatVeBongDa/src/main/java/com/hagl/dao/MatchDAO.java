package com.hagl.dao;

import java.sql.*;
import java.util.*;
import com.hagl.model.Match;
import java.time.format.DateTimeFormatter;

public class MatchDAO extends BaseDAO {

    public MatchDAO() { 
        super(); 
    }

    // Phương thức hiển thị TẤT CẢ các trận đấu trong CSDL (bao gồm cả quá khứ)
    public List<Match> getAllMatches() throws SQLException {
        List<Match> matchList = new ArrayList<>();
        
        // CÂU LỆNH SQL MỚI: Loại bỏ WHERE GETDATE() và sắp xếp theo ngày giảm dần (DESC)
        // để trận đấu gần nhất (dù là quá khứ hay tương lai) nằm ở trên cùng.
        String sql = "SELECT T.MaTD, T.DoiThu, T.NgayThiDau, T.GioThiDau, T.GiaVeCaoNhat, T.GiaVeThapNhat, S.TenSan "
                   + "FROM TRANDAU T JOIN SANVANDONG S ON T.MaSan = S.MaSan "
                   + "ORDER BY T.NgayThiDau DESC, T.GioThiDau DESC"; // Sắp xếp giảm dần để thấy các trận mới nhất (gần đây/tương lai)

        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Match match = new Match(
                    rs.getInt("MaTD"),
                    rs.getString("DoiThu"),
                    rs.getDate("NgayThiDau").toLocalDate(),
                    rs.getTime("GioThiDau").toLocalTime(),
                    rs.getString("TenSan"), 
                    rs.getDouble("GiaVeCaoNhat"), 
                    rs.getDouble("GiaVeThapNhat")
                );
                matchList.add(match);
            }
        } 
        return matchList;
    }
    
    // Giữ nguyên phương thức getMatchById(int matchId)
    public Match getMatchById(int matchId) throws SQLException {
        String sql = "SELECT T.*, S.TenSan FROM TRANDAU T JOIN SANVANDONG S ON T.MaSan = S.MaSan WHERE T.MaTD = ?";
        // ... (code đã cung cấp)
        return null;
    }
}