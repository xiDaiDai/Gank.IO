package com.isee.goose.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wuyun on 2015/10/16.
 */
public class BaseResult {
    @Expose
    @SerializedName("error")
    public boolean isError;
    @Expose
    @SerializedName("results")
    public List<AndroidInfo> androidInfoList;
}
