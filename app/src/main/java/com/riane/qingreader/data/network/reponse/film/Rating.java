package com.riane.qingreader.data.network.reponse.film;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiaobozheng on 8/14/2017.
 */

public class Rating implements Parcelable {
    private int min;
    private int max;
    private double average;
    private String stars;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.min);
        dest.writeInt(this.max);
        dest.writeDouble(this.average);
        dest.writeString(this.stars);
    }

    public Rating() {
    }

    protected Rating(Parcel in) {
        this.min = in.readInt();
        this.max = in.readInt();
        this.average = in.readDouble();
        this.stars = in.readString();
    }

    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel source) {
            return new Rating(source);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
