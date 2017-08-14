package com.riane.qingreader.data.network.reponse;

import com.riane.qingreader.data.network.reponse.film.Subject;

import java.util.List;

/**
 * Created by xiaobozheng on 8/14/2017.
 */

public class HotMovieBean {
    private int start;
    private int count;
    private String title;
    private int total;
    private List<Subject> subjects;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
