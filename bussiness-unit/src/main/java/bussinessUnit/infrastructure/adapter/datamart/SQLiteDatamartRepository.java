package bussinessUnit.infrastructure.adapter.datamart;

import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.infrastructure.port.DatamartRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatamartRepository implements DatamartRepository {
    private final String DB_URL = "jdbc:sqlite:datamart.db";

    public SQLiteDatamartRepository() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS oil_events (
                    date TEXT,
                    type TEXT,
                    value REAL,
                    ss TEXT,
                    PRIMARY KEY (date, type)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS news_events (
                    publishedAt TEXT PRIMARY KEY,
                    title TEXT,
                    content TEXT,
                    author TEXT,
                    description TEXT,
                    url TEXT,
                    urlToImage TEXT,
                    id TEXT,
                    name TEXT,
                    source TEXT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS recent_oil_events (
                    date TEXT,
                    type TEXT,
                    value REAL,
                    ss TEXT,
                    PRIMARY KEY (date, type)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS recent_news_events (
                    publishedAt TEXT PRIMARY KEY,
                    title TEXT,
                    content TEXT,
                    author TEXT,
                    description TEXT,
                    url TEXT,
                    urlToImage TEXT,
                    id TEXT,
                    name TEXT,
                    source TEXT
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOilEvent(OilEvent event) {
        String sql = "INSERT OR IGNORE INTO oil_events (date, value, type, ss) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTsAsString());
            ps.setDouble(2, event.getValue());
            ps.setString(3, event.getType());
            ps.setString(4, event.getSs());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveRecentOilEvent(OilEvent event) {
        String recentSql = "INSERT OR REPLACE INTO recent_oil_events (date, value, type, ss) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(recentSql)) {

            ps.setString(1, event.getTsAsString());
            ps.setDouble(2, event.getValue());
            ps.setString(3, event.getType());
            ps.setString(4, event.getSs());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OilEvent> getLastOilEvents(int count) {
        List<OilEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM recent_oil_events ORDER BY date DESC LIMIT ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new OilEvent(
                        Instant.parse(rs.getString("date")),
                        rs.getDouble("value"),
                        rs.getString("ss"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void saveNewsEvent(NewsEvent event) {
        String insertSql = """
            INSERT OR IGNORE INTO news_events 
            (publishedAt, title, content, author, description, url, urlToImage, id, name, source) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            String sourceJson = new Gson().toJson(event.getSource());

            ps.setString(1, event.getPublishedAt());
            ps.setString(2, event.getTitle());
            ps.setString(3, event.getContent());
            ps.setString(4, event.getAuthor());
            ps.setString(5, event.getDescription());
            ps.setString(6, event.getUrl());
            ps.setString(7, event.getUrlToImage());
            ps.setString(8, event.getId());
            ps.setString(9, event.getName());
            ps.setString(10, sourceJson);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveRecentNewsEvent(NewsEvent event) {
        String recentInsertSql = """
            INSERT OR REPLACE INTO recent_news_events 
            (publishedAt, title, content, author, description, url, urlToImage, id, name, source) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(recentInsertSql)) {

            String sourceJson = new Gson().toJson(event.getSource());

            ps.setString(1, event.getPublishedAt());
            ps.setString(2, event.getTitle());
            ps.setString(3, event.getContent());
            ps.setString(4, event.getAuthor());
            ps.setString(5, event.getDescription());
            ps.setString(6, event.getUrl());
            ps.setString(7, event.getUrlToImage());
            ps.setString(8, event.getId());
            ps.setString(9, event.getName());
            ps.setString(10, sourceJson);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NewsEvent> getLastNewsEvents(int count) {
        List<NewsEvent> list = new ArrayList<>();
        String sql = """
            SELECT * FROM recent_news_events 
            ORDER BY publishedAt DESC 
            LIMIT ?;
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String sourceJson = rs.getString("source");
                JsonObject source = new Gson().fromJson(sourceJson, JsonObject.class);

                list.add(new NewsEvent(
                        rs.getString("title"),
                        source,
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("url"),
                        rs.getString("urlToImage"),
                        rs.getString("publishedAt"),
                        rs.getString("content")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OilEvent> getHistoricOilsForGraphs(String type, Instant fromDate) {
        List<OilEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM oil_events WHERE type = ? AND date >= ? ORDER BY date ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setString(2, fromDate.toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OilEvent(
                        Instant.parse(rs.getString("date")),
                        rs.getDouble("value"),
                        rs.getString("ss"),
                        rs.getString("type")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
