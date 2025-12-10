package com.hagl.controller;

import com.hagl.dao.TicketDAO;
import com.hagl.model.Ticket;
import com.hagl.model.User;
import com.hagl.vnpay.VnPayConfig; // Lớp cấu hình VNPAY (cần có)
import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private TicketDAO ticketDAO;

    public void init() {
        ticketDAO = new TicketDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login"); 
            return;
        }

        // 2. Lấy thông tin đặt vé từ form
        int matchId = Integer.parseInt(request.getParameter("matchId"));
        String seatLocationsStr = request.getParameter("seats"); 
        double finalPrice = Double.parseDouble(request.getParameter("finalPrice"));
        
        String[] seats = seatLocationsStr.split(",");
        long totalAmount = (long) (finalPrice * seats.length); // Tổng tiền (VND)
        
        // 3. Tạo Vé PENDING và lưu ID
        List<Integer> ticketIds = new ArrayList<>();
        String orderInfo = "Dat ve HAGL tran MaTD=" + matchId;
        
        try {
            for (String seat : seats) {
                Ticket ticket = new Ticket(matchId, currentUser.getUserId(), seat, finalPrice, "Pending");
                int ticketId = ticketDAO.insertPendingTicket(ticket);
                if (ticketId > 0) {
                    ticketIds.add(ticketId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi CSDL khi tạo vé. Vui lòng thử lại.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        
        // 4. Tạo yêu cầu VNPAY
        // Lưu ID của các vé vào Session để VnPayReturnServlet sử dụng
        session.setAttribute("pendingTicketIds", ticketIds); 
        
        String vnpayUrl = createVnPayRequestUrl(request, totalAmount, orderInfo, ticketIds);
        
        // 5. Chuyển hướng sang cổng thanh toán VNPAY
        response.sendRedirect(vnpayUrl);
    }
    
    // Phương thức tạo URL VNPAY (Cần có VnPayConfig và hmacSHA512)
    private String createVnPayRequestUrl(HttpServletRequest request, long amount, String orderInfo, List<Integer> ticketIds) throws IOException {
        
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis()); // Mã giao dịch duy nhất
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode); 
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // Nhân 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef); 
        vnp_Params.put("vnp_OrderInfo", orderInfo); 
        
        // Lưu danh sách ID vé vào vnp_OrderType (MaVe1-MaVe2-...)
        String ticketRef = "";
        for (int id : ticketIds) { ticketRef += id + "-"; }
        vnp_Params.put("vnp_OrderType", ticketRef.substring(0, ticketRef.length() - 1)); 
        
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());
        
        // Các tham số ngày giờ (cần SimpleDateFormat từ VnPayConfig)
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        vnp_Params.put("vnp_CreateDate", VnPayConfig.sdf.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15); // Thời gian hết hạn 15 phút
        vnp_Params.put("vnp_ExpireDate", VnPayConfig.sdf.format(cld.getTime()));
        
        // Sắp xếp và tạo chuỗi Hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())).append('&');
            }
        }
        
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString()); 
        
        queryUrl += "vnp_SecureHash=" + vnp_SecureHash;
        return VnPayConfig.vnp_PayUrl + "?" + queryUrl;
    }
}