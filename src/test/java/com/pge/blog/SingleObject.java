package com.pge.blog;

import com.sun.javaws.IconUtil;

public class SingleObject {
    private static SingleObject instance = new SingleObject();

    private SingleObject(){}

    public static SingleObject getInstance(){
        return instance;
    }

    public void show(){
        System.out.println("Hello PGE");
    }
}
