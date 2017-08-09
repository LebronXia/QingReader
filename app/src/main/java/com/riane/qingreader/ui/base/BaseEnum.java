package com.riane.qingreader.ui.base;

/**
 * Created by Riane on 2017/8/7.
 */

public enum  BaseEnum {
    all("all"), Android("Andorid"), qianduan("前端"), expand("拓展资源"),fuli("福利"), video("休息视频");

    String value;

    private BaseEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
