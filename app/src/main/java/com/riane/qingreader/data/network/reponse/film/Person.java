package com.riane.qingreader.data.network.reponse.film;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiaobozheng on 8/14/2017.
 */

public class Person implements Parcelable {
    //电影图片
    private String alt;
    //导演或演员
    private String type;
    private Image avatars;
    private String id;
    private String name;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Image getAvatars() {
        return avatars;
    }

    public void setAvatars(Image avatars) {
        this.avatars = avatars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alt);
        dest.writeString(this.type);
        dest.writeParcelable(this.avatars, flags);
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.alt = in.readString();
        this.type = in.readString();
        this.avatars = in.readParcelable(Image.class.getClassLoader());
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
