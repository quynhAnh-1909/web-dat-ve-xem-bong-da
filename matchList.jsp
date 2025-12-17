<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HAGL FC - Official Ticket Store</title>
<style>
    :root {
        --hagl-green: #004d31;
        --hagl-red: #e41e31;
        --bg-color: #f0f2f5; /* Nền xám nhạt cao cấp */
        --card-bg: #ffffff;
        --text-main: #2d3436;
        --text-muted: #636e72;
    }

    body { 
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
        background-color: var(--bg-color); 
        margin: 0; 
        color: var(--text-main);
    }
    
    .hero-banner {
        width: 100%; height: 350px;
        background: linear-gradient(rgba(0,0,0,0.4), rgba(0,0,0,0.4)), url('${root}/resources/hagl_banner.jpg');
        background-size: cover; background-position: center;
        display: flex; flex-direction: column; align-items: center; justify-content: center;
        color: white;
    }

    .hero-banner h1 { 
        font-size: 2.8rem; font-weight: 800; letter-spacing: 1px; margin: 0;
        text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
    }

    .content-wrapper { max-width: 1200px; margin: 40px auto; padding: 0 20px; }
    
    .section-header {
        display: flex; align-items: center; justify-content: space-between;
        margin-bottom: 30px; border-bottom: 2px solid #dfe6e9; padding-bottom: 15px;
    }
    
    .section-title { font-size: 1.6rem; color: var(--hagl-green); margin: 0; text-transform: uppercase; }

    .match-grid { 
        display: grid; 
        grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); 
        gap: 30px; 
    }

    .match-card { 
        background: var(--card-bg); 
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 20px rgba(0,0,0,0.06);
        transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
        display: flex; flex-direction: column;
    }

    .match-card:hover { 
        transform: translateY(-8px);
        box-shadow: 0 12px 30px rgba(0,0,0,0.12);
    }
    
    /* LOGO AREA - KÍCH THƯỚC BỰ */
    .logo-area { 
        padding: 10px; 
        background: #fff;
        display: flex; 
        align-items: center; 
        justify-content: center;
        height: 220px; 
        position: relative;
    }
    
    .card-accent {
        position: absolute; top: 0; left: 0; width: 100%; height: 4px;
        background: var(--hagl-green);
    }

    .logo-area img { 
        max-width: 95%; 
        max-height: 200px; 
        object-fit: contain; 
        transition: transform 0.3s ease;
    }
    
    .match-card:hover .logo-area img {
        transform: scale(1.08);
    }

    .card-body { padding: 25px; text-align: center; }
    
    .match-title { 
        font-size: 1.4rem; font-weight: 700; margin-bottom: 15px;
        color: var(--hagl-green);
    }
    
    .info-row { 
        display: flex; align-items: center; justify-content: center; gap: 10px;
        font-size: 1rem; color: var(--text-muted); margin-bottom: 8px;
    }

    .stadium-info { font-size: 0.9rem; color: #b2bec3; margin-bottom: 20px; }

    /* GIÁ TIỀN - BỎ SỐ LẺ VÀ THÊM CHỮ "GIÁ TỪ" */
    .price-tag { 
        font-size: 1.3rem; font-weight: 700; color: var(--hagl-red);
        background: #fff5f6; padding: 10px 20px; border-radius: 8px;
        display: inline-flex;
        align-items: center;
        margin-bottom: 20px;
    }

    .price-label {
        font-size: 0.9rem; 
        font-weight: 400; 
        color: #636e72; 
        margin-right: 8px;
    }

    .btn-ticket {
        display: block; width: 100%;
        background-color: var(--hagl-green); color: white;
        text-decoration: none; padding: 16px 0;
        border-radius: 8px; font-weight: 600; font-size: 1.1rem;
        transition: background 0.2s;
    }

    .btn-ticket:hover { background-color: #003321; }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main class="content-wrapper">
        <div class="section-header">
            <h2 class="section-title">Trận Đấu Sắp Tới</h2>
        </div>
        
        <div class="match-grid">
            <c:forEach var="match" items="${matchList}">
                <div class="match-card">
                    <div class="logo-area">
                        <div class="card-accent"></div>
                        <img src="${root}/resources/${match.opponentImageName}" alt="${match.opponent}">
                    </div>
                    <div class="card-body">
                        <div class="match-title">HAGL VS ${match.opponent}</div>
                        
                        <div class="info-row">
                            <span>📅 ${match.matchDate}</span>
                            <span>|</span>
                            <span>⏰ ${match.matchTime}</span>
                        </div>
                        
                        <div class="stadium-info">Địa điểm: ${match.stadiumName}</div>
                        
                        <div class="price-tag">
                            <span class="price-label">Giá từ:</span>
                            <fmt:formatNumber value="${match.minPrice}" type="currency" currencyCode="VND" maxFractionDigits="0"/>
                        </div>

                        <a href="${root}/matchDetail?id=${match.matchId}" class="btn-ticket">
                            MUA VÉ NGAY
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>
    
    <jsp:include page="footer.jsp" />
</body>
</html>