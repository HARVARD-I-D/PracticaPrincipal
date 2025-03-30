package org.example;

public class Main {
    public static void main(String[] args) {
        NewProvider newProvider = new NewsAPIProvider();
        newProvider.provide();
    }
}
