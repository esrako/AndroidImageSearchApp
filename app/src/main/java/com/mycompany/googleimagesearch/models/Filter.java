package com.mycompany.googleimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ekucukog on 5/13/2015.
 */
public class Filter implements Parcelable {

    //private static final long serialVersionUID = 1L;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeString(this.type);
        dest.writeString(this.site);
    }

    private Filter(Parcel in) {
        this.size = in.readString();
        this.color = in.readString();
        this.type = in.readString();
        this.site = in.readString();
    }

    public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>() {
        public Filter createFromParcel(Parcel source) {
            return new Filter(source);
        }

        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}
