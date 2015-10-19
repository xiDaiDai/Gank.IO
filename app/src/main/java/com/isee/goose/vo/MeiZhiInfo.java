package com.isee.goose.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wuyun on 2015/10/16.
 */
public class MeiZhiInfo {
    @Expose
    @SerializedName("who")
    public String presenter;
    @Expose
    @SerializedName("publishedAt")
    public  String time;
    @Expose
    @SerializedName("desc")
    public String desc;
    @Expose
    @SerializedName("url")
    public String url;

}
