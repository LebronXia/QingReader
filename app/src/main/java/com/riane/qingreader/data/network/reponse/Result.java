package com.riane.qingreader.data.network.reponse;

/**
 * Created by xiaobozheng on 8/11/2017.
 */
public class Result {
    private String desc ;
    private String ganhuo_id;
    private String publishedAt;
    private String readability;
    private String type;
    private String url;
    private String who;

    public Result(String desc, String ganhuo_id, String publishedAt,
            String readability, String type, String url, String who) {
        this.desc = desc;
        this.ganhuo_id = ganhuo_id;
        this.publishedAt = publishedAt;
        this.readability = readability;
        this.type = type;
        this.url = url;
        this.who = who;
    }

    public Result() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGanhuo_id() {
        return ganhuo_id;
    }

    public void setGanhuo_id(String ganhuo_id) {
        this.ganhuo_id = ganhuo_id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getReadability() {
        return readability;
    }

    public void setReadability(String readability) {
        this.readability = readability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
