package com.udacity.jwdnd.course1.cloudstorage;

public class Util {
    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
