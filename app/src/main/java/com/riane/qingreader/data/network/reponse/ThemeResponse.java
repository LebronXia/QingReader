package com.riane.qingreader.data.network.reponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Riane on 2017/8/10.
 */

public class ThemeResponse implements Serializable{
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
