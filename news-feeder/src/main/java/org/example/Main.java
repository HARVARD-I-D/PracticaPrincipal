package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        NewProvider newProvider = new NewsAPIProvider(args[0]);
        newProvider.provide();
        SQLiteNewStore newStore = new SQLiteNewStore();
        new NewController(newProvider, newStore).run();
    }
}