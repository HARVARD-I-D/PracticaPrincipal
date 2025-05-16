package subscriberHexagonal.adapter.out;

import subscriberHexagonal.domain.port.out.EventStore;
import subscriberHexagonal.domain.model.OilEvent;
import subscriberHexagonal.domain.model.OilEventParser;

import java.sql.*;

public class MultiEventStore implements EventStore {
    private static final String url = "jdbc:sqlite:events.db";

    public MultiEventStore(){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            String sqlOil = "CREATE TABLE IF NOT EXISTS oil_prices (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "value REAL, " +
                    "date TEXT, " +
                    "type TEXT)";
            stmt.execute(sqlOil);

            String sqlNews = "CREATE TABLE IF NOT EXISTS news (" +
                    "id TEXT, " +
                    "name TEXT, " +
                    "author TEXT, " +
                    "title TEXT, " +
                    "description TEXT, " +
                    "url TEXT, " +
                    "urlToImage TEXT, " +
                    "publishedAt TEXT, " +
                    "content TEXT, " +
                    "source TEXT)";
            stmt.execute(sqlNews);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeOil(String oilPriceRaw) {
        OilEvent event = OilEventParser.parse(oilPriceRaw);
        String sql = "INSERT INTO oil_prices (value, date, type) VALUES (?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, event.getValue());
            pstmt.setString(2, event.getTsAsString());
            pstmt.setString(3, event.getType());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void storeNews(String newsRaw) {

    }
}
