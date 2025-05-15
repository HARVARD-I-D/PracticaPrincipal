package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MultiEventStore implements EventStore{
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
    public void storeOil(String oilPrice) {

    }

    @Override
    public void storeNews(String news) {

    }
}
