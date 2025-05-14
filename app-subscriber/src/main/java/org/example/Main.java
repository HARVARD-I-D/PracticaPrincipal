package org.example;

public class Main {
    public static void main(String[] args){
        EventReceiver receiver = new MultiEventReceiver();
        receiver.start();
    }
}
