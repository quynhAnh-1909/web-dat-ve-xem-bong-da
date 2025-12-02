package dao;

import model.Match;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class MatchDAO {
    private static List<Match> matchList = new ArrayList<>();

    static {
        // Khởi tạo dữ liệu mẫu
        matchList.add(new Match(1, "Viettel FC", LocalDate.of(2025, 12, 15), LocalTime.of(17, 0), "Sân Pleiku", 150000));
        matchList.add(new Match(2, "Hà Nội FC", LocalDate.of(2025, 12, 29), LocalTime.of(17, 0), "Sân Pleiku", 200000));
    }

    public List<Match> getAllMatches() {
        return matchList;
    }
    
    public Match getMatchById(int matchId) {
        for (Match match : matchList) {
            if (match.getMatchId() == matchId) {
                return match;
            }
        }
        return null;
    }
}
// Bạn sẽ tạo các lớp TicketDAO, UserDAO, AreaDAO tương tự khi cần.