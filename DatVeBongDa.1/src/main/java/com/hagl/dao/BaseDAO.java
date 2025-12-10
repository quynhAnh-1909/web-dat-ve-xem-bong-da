package com.hagl.dao;

import javax.sql.DataSource;
import javax.naming.*;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {
    protected DataSource dataSource; 
    private final String JNDI_NAME = "jdbc/FootballDB";

    public BaseDAO() {
        try {
            // Thực hiện tra cứu JNDI để lấy DataSource (Connection Pool)
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            this.dataSource = (DataSource) envContext.lookup(JNDI_NAME);
            
            if (this.dataSource == null) {
                throw new RuntimeException("Lỗi: DataSource JNDI '" + JNDI_NAME + "' không tìm thấy.");
            }
        } catch (NamingException e) {
            System.err.println("Lỗi cấu hình JNDI: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi cấu hình CSDL.", e);
        }
    }

    /** Phương thức để các DAO con gọi khi cần kết nối */
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}