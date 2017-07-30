package com.riane.qingreader.data.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Riane on 2017/7/30.
 */

@Entity
public class SearchHistory {
    @Id
    private Long id;
    private String searchContent;

    @Generated(hash = 495444001)
    public SearchHistory(Long id, String searchContent) {
        this.id = id;
        this.searchContent = searchContent;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
