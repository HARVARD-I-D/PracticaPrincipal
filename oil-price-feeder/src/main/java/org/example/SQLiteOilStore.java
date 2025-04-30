package org.example;
import java.sql.*;

public class SQLiteOilStore implements OilStore {
    private static final String URL = "jdbc:sqlite:oil_prices.db";

    public SQLiteOilStore() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS oil_prices (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "value REAL, " +
                    "date TEXT, " +
                    "type TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(OilPrice oilPrice) {
        String sql = "INSERT INTO oil_prices (value, date, type) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, oilPrice.getValue());
            pstmt.setString(2, oilPrice.getTsAsString());
            pstmt.setString(3, oilPrice.getType().toString());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}