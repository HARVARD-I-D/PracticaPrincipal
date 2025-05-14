package org.example;

import java.sql.*;

public class SQLiteNewStore implements NewStore{
    private static final String url = "jdbc:sqlite:news.db";


    public SQLiteNewStore(){
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS news (" + "source" + "id" + "name" +
                    "author" + "title" + "description" + "url" +
                    "urlToImage" + "publishedAt" + "content";
            statement.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void save(New New){
        String sql = "INSERT INTO news (source, id, name, author, title, description, url, urlToImage, publishedAt, content) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, New.getAuthor());
            preparedStatement.setString(2, New.getTitle());
            preparedStatement.setString(3, New.getDescription());
            preparedStatement.setString(4, New.getUrl());
            preparedStatement.setString(5, New.getUrlToImage());
            preparedStatement.setString(6, New.getPublishedAt());
            preparedStatement.setString(7, New.getContent());
            preparedStatement.setString(8, New.getId());
            preparedStatement.setString(9, New.getName());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
