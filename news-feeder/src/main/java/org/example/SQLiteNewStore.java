package org.example;

import java.sql.*;

public class SQLiteNewStore implements NewStore{
    private static final String url = "jdbc:sqlite:news.db";

    public SQLiteNewStore(){
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS news (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "source TEXT,\n" +
                    "idsource TEXT,\n" +
                    "name TEXT,\n" +
                    "author TEXT,\n" +
                    "title TEXT,\n" +
                    "description TEXT,\n" +
                    "url TEXT,\n" +
                    "urlToImage TEXT,\n" +
                    "date TEXT,\n" +
                    "content TEXT)";
            statement.execute(sql);
            //statement.execute("DROP TABLE news");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void save(New New){
        String sql = "INSERT INTO news (source, idsource, name, author, title, description, url, urlToImage, date, content) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, New.getSourceAsMap().toString());
            preparedStatement.setString(2, New.getId());
            preparedStatement.setString(3, New.getName());
            preparedStatement.setString(4, New.getAuthor());
            preparedStatement.setString(5, New.getTitle());
            preparedStatement.setString(6, New.getDescription());
            preparedStatement.setString(7, New.getUrl());
            preparedStatement.setString(8, New.getUrlToImage());
            preparedStatement.setString(9, New.getPublishedAt());
            preparedStatement.setString(10, New.getContent());
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
