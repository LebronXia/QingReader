package com.riane.qingreader.data.network.reponse;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by xiaobozheng on 10/11/2017.
 */

@Entity
public class ResultBean {
    @Id
    private String _id;
    @SerializedName("creatAt")
    private String createdAt;
    @SerializedName("desc")
    private String desc;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String who;
    //@Transient  表示不存在数据库
    @SerializedName("images")
    @Transient
    private List<String> images;

    @Generated(hash = 809045397)
    public ResultBean(String _id, String createdAt, String desc,
            String publishedAt, String source, String type, String url,
            boolean used, String who) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    @Generated(hash = 2137771703)
    public ResultBean() {
    }

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }

    @Override
    public String toString() {
        return "ResultsBean{" +
                "who='" + who + '\'' +
                ", used=" + used +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public List<String> getImages() {
        return images;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
