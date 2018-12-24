package com.example;

import com.gmail.xfuflo.commons.utils.KeySyncExec;
import com.gmail.xfuflo.commons.utils.SharedValueMap;

import java.util.concurrent.locks.Lock;

public class test_test {

    public static void main(String[] args) {
        SharedValueMap<String, String> map = new SharedValueMap<>();
        KeySyncExec kse = new KeySyncExec();

        System.out.println("Hello world!");
    }

}
