package com.relyme.linkOccupation;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");

    }


    public static void main(String[] args) {
        System.out.println("格式前"+UUID.randomUUID().toString());
        System.out.println("格式后"+getUUID());
    }
}
