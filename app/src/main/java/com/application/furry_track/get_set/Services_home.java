package com.application.furry_track.get_set;

import java.io.Serializable;


public class Services_home implements Serializable {
    public int serviceid;

    public int getBack_bg() {
        return back_bg;
    }

    public void setBack_bg(int back_bg) {
        this.back_bg = back_bg;
    }

    public int back_bg;
    public String name;

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String count;
    public int imageUrl;

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
