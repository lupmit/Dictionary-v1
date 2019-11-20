package com.example.dictionaryapp;

import android.text.Spanned;

public class AnhvietModel  {
    private  String name1;
    private Spanned name2;
    public  AnhvietModel(){}

    public AnhvietModel(String name1, Spanned name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Spanned getName2() {
        return name2;
    }

    public void setName2(Spanned name2) {
        this.name2 = name2;
    }
}
