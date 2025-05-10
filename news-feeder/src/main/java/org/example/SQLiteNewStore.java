package org.example;

import java.sql.*;

public class SQLiteNewStore implements NewStore{
    private static final String url = "jdbc:sqlite:news.db";

    /*
            String[] source;
        String author;
        String title;
        String description;
        String url;
        String urlToImage;
        String publishedAt;
        String content;
            */

    public SQLiteNewStore(){
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS news (" + "source" + "author" +
                    "title" + "description" + "url" +
                    "urlToImage" + "publishedAt" + "content";
            statement.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void save(New New){
        String sql = "INSERT INTO news (source, author, title, description, url, urlToImage, publishedAt, content) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setArray(1, New.getSource());
            preparedStatement.setString(2, New.getAuthor());
            preparedStatement.setString(3, New.getTitle());
            preparedStatement.setString(1, New.getDescription());
            preparedStatement.setString(1, New.getUrl());
            preparedStatement.setString(1, New.getUrlToImage());
            preparedStatement.setString(1, New.getPublishedAt());
            preparedStatement.setString(1, New.getContent());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
