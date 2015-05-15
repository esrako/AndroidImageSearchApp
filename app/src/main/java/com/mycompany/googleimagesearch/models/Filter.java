package com.mycompany.googleimagesearch.models;

import java.io.Serializable;

/**
 * Created by ekucukog on 5/13/2015.
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;
    public String size;
    public String color;
    public String type;
    public String site;

    public Filter(String size, String color, String type, String site){
        this.size = size;
        this.color = color;
        this.type = type;
        this.site = site;
    }

    public Filter(){
        this("", "", "", "");
    }

    public String toString(){
        return "Size: " + size + ", Color: " + color + ", Type: " + type + ", Site: " + site;
    }
}
