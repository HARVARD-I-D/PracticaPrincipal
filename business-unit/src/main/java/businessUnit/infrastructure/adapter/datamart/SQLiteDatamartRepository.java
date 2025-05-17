package businessUnit.infrastructure.adapter.datamart;

import businessUnit.application.domain.model.OilEvent;
import businessUnit.application.domain.model.NewsEvent;
import businessUnit.infrastructure.port.DatamartRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatamartRepository implements DatamartRepository{
    private final String DB_URL = "jdbc:sqlite:datamart.db";

    public SQLiteDatamartRepository(){
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS oil_event (
                    timestamp TEXT PRIMARY KEY,
                    value REAL,
                    type TEXT,
                    ss TEXT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS news_event (
                    timestamp TEXT PRIMARY KEY,
                    title TEXT,
                    content TEXT,
                    source TEXT
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOilEvent(OilEvent event) {
    }

    @Override
    public List<OilEvent> getLastOilEvents(int count){
        return null;
    }

    @Override
    public void saveNewsEvent(NewsEvent event) {
    }

    @Override
    public List<NewsEvent> getLastNewsEvents(int count){
        return null;
    }


}
