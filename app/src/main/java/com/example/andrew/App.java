package com.example.andrew.midterm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andrew on 10/24/16.
 */

public class App implements Parcelable {

    String price, name, img;

    public App() {
    }

    public App(String price, String name, String img) {
        this.price = price;
        this.name = name;
        this.img = img;
    }

    protected App(Parcel in) {
        price = in.readString();
        name = in.readString();
        img = in.readString();
    }

    public static final Creator<App> CREATOR = new Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "App{" +
                "price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(name);
        dest.writeString(img);
    }
}
