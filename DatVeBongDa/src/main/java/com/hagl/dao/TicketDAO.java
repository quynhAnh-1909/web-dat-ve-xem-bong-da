package com.hagl.dao;

import com.hagl.model.Ticket;
import java.sql.*;
import java.time.LocalDateTime;

public class TicketDAO extends BaseDAO {

    public TicketDAO() { super(); }

    /**
     * Tạo một vé mới với trạng thái PENDING.
     * @param ticket đối tượng Ticket
     * @return MaVe (TicketId) nếu thành công, -1 nếu thất bại.
     */
    public int insertPendingTicket(Ticket ticket) throws SQLException {
        // Sử dụng Statement.RETURN_GENERATED_KEYS để lấy ID tự động tăng (MaVe)
        String sql = "INSERT INTO VE (MaTD, MaND, ViTriNgoi, GiaVe, TrangThai) VALUES (?, ?, ?, ?, 'Pending')";
        int generatedId = -1;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, ticket.getMatchId());
            // Xử lý MaND (UserId)
            if (ticket.getUserId() > 0) {
                ps.setInt(2, ticket.getUserId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setString(3, ticket.getSeatLocation());
            ps.setDouble(4, ticket.getFinalPrice());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        }
        return generatedId;
    }

    /**
     * Cập nhật trạng thái và thời gian thanh toán cho vé.
     */
    public boolean updateTicketStatus(int ticketId, String status) throws SQLException {
        // UPDATE VE SET TrangThai = 'Success', ThoiGianDat = GETDATE() WHERE MaVe = ?
        String sql = "UPDATE VE SET TrangThai = ?, ThoiGianDat = GETDATE() WHERE MaVe = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status); // Ví dụ: 'Success' hoặc 'Failed'
            ps.setInt(2, ticketId);
            
            return ps.executeUpdate() > 0;
        }
    }
}