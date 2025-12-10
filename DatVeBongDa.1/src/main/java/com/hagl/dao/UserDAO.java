package com.hagl.dao;

import com.hagl.model.User;
import java.sql.*;

public class UserDAO extends BaseDAO {

    public UserDAO() { super(); }

    /**
     * Kiểm tra email và mật khẩu trong bảng NGUOIDUNG để đăng nhập.
     */
    public User checkLogin(String email, String password) throws SQLException {
        // SELECT * FROM NGUOIDUNG WHERE Email = ? AND MatKhau = ?
        String sql = "SELECT MaND, HoTen, Email, SoDienThoai FROM NGUOIDUNG WHERE Email = ? AND MatKhau = ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password); 
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("MaND"));
                    user.setFullName(rs.getString("HoTen"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhoneNumber(rs.getString("SoDienThoai"));
                    return user;
                }
            }
        }
        return null;
    }
    
    /**
     * Chèn thông tin người dùng mới vào bảng NGUOIDUNG.
     * @return true nếu đăng ký thành công, false nếu email đã tồn tại.
     */
    public boolean registerUser(User user) throws SQLException {
        // Kiểm tra Email đã tồn tại trước khi chèn để tránh lỗi UNIQUE KEY
        if (isEmailExists(user.getEmail())) {
            return false; // Email đã tồn tại
        }

        // INSERT INTO NGUOIDUNG (HoTen, Email, MatKhau, SoDienThoai) VALUES (?, ?, ?, ?)
        String sql = "INSERT INTO NGUOIDUNG (HoTen, Email, MatKhau, SoDienThoai) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Lấy ID tự động tăng (MaND) cho đối tượng User
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Kiểm tra xem Email đã tồn tại trong CSDL hay chưa.
     */
    private boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(MaND) FROM NGUOIDUNG WHERE Email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}