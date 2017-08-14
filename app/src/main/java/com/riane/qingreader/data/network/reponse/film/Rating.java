package com.riane.qingreader.data.network.reponse.film;

/**
 * Created by xiaobozheng on 8/14/2017.
 */

public class Rating {
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
}
