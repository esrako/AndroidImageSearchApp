package com.mycompany.googleimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ekucukog on 5/12/2015.
 */
public class ImageResult implements Parcelable {

    //private static final long serialVersionUID = 0L;
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult (JSONObject json){
        try{
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        for(int i=0; i<array.length(); i++){
            try{
                results.add(new ImageResult(array.getJSONObject(i)));
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullUrl);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.title);
    }

    private ImageResult(Parcel in) {
        this.fullUrl = in.readString();
        this.thumbUrl = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
