package com.isee.goose.vo;

import android.support.v4.app.Fragment;

/**
 * Created by wuyun on 2015/9/23.
 */
public class MenuItem {

    public enum FragmentType {
        ANDROID, MEIZHI
    }


    private String title;
    private int resourceId;
    private FragmentType type;
    private Class<? extends Fragment> fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Class<? extends Fragment> getFragment() {
        return fragment;
    }

    public void setFragment(Class<? extends Fragment> fragment) {
        this.fragment = fragment;
    }


    public FragmentType getType() {
        return type;
    }

    public void setType(FragmentType type) {
        this.type = type;
    }

    public MenuItem() {
    }

    public MenuItem(String title, int resourceId, Class<? extends Fragment> fragment) {
        this.resourceId = resourceId;
        this.title = title;
        this.fragment = fragment;
    }

    public MenuItem(String title, int resourceId, FragmentType type, Class<? extends Fragment> fragment) {
        this.title = title;
        this.resourceId = resourceId;
        this.type = type;
        this.fragment = fragment;
    }

    public MenuItem(String title,FragmentType type, Class<? extends Fragment> fragment) {
        this.title = title;
        this.type = type;
        this.fragment = fragment;
    }
}
