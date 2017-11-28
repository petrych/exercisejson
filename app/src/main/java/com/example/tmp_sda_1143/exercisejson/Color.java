package com.example.tmp_sda_1143.exercisejson;

/**
 * Created by tmp-sda-1143 on 11/27/17.
 */

public class Color {
    String colorString;
    String category;
    String type;
    Code code;

    public Color(String colorString, String category, String type, Code code) {
        this.colorString = colorString;
        this.category = category;
        this.type = type;
        this.code = code;
    }

    public Color(String colorString, String category, Code code) {
        this.colorString = colorString;
        this.category = category;
        this.code = code;
    }
}

