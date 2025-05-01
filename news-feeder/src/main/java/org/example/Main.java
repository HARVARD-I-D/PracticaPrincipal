package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        NewProvider newProvider = new NewsAPIProvider();
        newProvider.provide(Arrays.toString(args));
    }
}