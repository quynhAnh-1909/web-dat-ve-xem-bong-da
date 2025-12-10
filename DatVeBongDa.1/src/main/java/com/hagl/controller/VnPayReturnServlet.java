package com.hagl.controller;

import com.hagl.dao.TicketDAO;
import com.hagl.vnpay.VnPayConfig;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/vnpay_return")
public class VnPayReturnServlet extends HttpServlet {
    private TicketDAO ticketDAO;

    public void init() {
        ticketDAO = new TicketDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        // 1. Lấy tất cả tham số trả về
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            fields.put(fieldName, fieldValue);
        }
        
        String vnp_SecureHash = fields.get("vnp_SecureHash");
        fields.remove("vnp_SecureHash"); // Loại bỏ hash ra khỏi chuỗi xác thực

        // 2. Tái tạo chuỗi hash và so sánh
        String hashSecret = VnPayConfig.vnp_HashSecret;
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())).append('&');
            }
        }
        
        // Xóa dấu '&' cuối cùng và tạo hash
        String hashDataStr = hashData.toString().substring(0, hashData.length() - 1);
        String secureHash = VnPayConfig.hmacSHA512(hashSecret, hashDataStr);
        
        if (secureHash.equals(vnp_SecureHash)) {
            // 3. Xác thực giao dịch (Hash hợp lệ)
            String vnp_ResponseCode = fields.get("vnp_ResponseCode");
            String vnp_TransactionStatus = fields.get("vnp_TransactionStatus");
            String ticketRefStr = fields.get("vnp_OrderType"); // Chuỗi MaVe1-MaVe2-...

            String status;
            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                status = "Success"; // Giao dịch thành công
            } else {
                status = "Failed";  // Giao dịch thất bại
            }
            
            // 4. Cập nhật trạng thái vé trong CSDL
            try {
                String[] ticketIdsStr = ticketRefStr.split("-");
                for (String idStr : ticketIdsStr) {
                    int ticketId = Integer.parseInt(idStr);
                    ticketDAO.updateTicketStatus(ticketId, status);
                }
                
                request.setAttribute("paymentStatus", status);
                request.setAttribute("vnp_TxnRef", fields.get("vnp_TxnRef"));
                
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("paymentStatus", "Error");
                request.setAttribute("transactionStatus", "Lỗi CSDL khi cập nhật trạng thái vé.");
            }
            
        } else {
            // 5. Lỗi Hash: Báo lỗi bảo mật
            request.setAttribute("paymentStatus", "Error");
            request.setAttribute("transactionStatus", "Lỗi xác thực dữ liệu (Secure Hash invalid).");
        }
        
        // 6. Chuyển hướng đến trang kết quả thanh toán
        request.getRequestDispatcher("paymentResult.jsp").forward(request, response);
    }
}