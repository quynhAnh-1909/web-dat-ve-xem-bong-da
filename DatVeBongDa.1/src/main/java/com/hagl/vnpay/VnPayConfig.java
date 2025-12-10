package com.hagl.vnpay;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VnPayConfig {
    
    // Cấu hình VNPAY (THAY THẾ BẰNG THÔNG TIN THẬT CỦA BẠN)
    public static final String vnp_PayUrl = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ReturnUrl = "http://localhost:8080/HAGLBookingWeb/vnpay_return"; // Đổi thành URL của bạn
    public static final String vnp_TmnCode = "YOUR_TMN_CODE"; // Mã Terminal
    public static final String vnp_HashSecret = "YOUR_HASH_SECRET"; // Secret Key
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    
    // Phương thức băm HMAC-SHA512
    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            byte[] keyBytes = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.US_ASCII.toString());
            byte[] result = hmacSHA512.doFinal(dataBytes);
            
            Formatter formatter = new Formatter();
            for (byte b : result) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}