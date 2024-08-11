package com.example.testnotificationapp;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class NotificationResult {
    private String smallIcon;
    private String largeIcon;
    private String title;
    private String body;
    private String bigPicture;
    private String launchURL;
    private JSONObject jsonObject;
    private JSONObject additionalData;

    public NotificationResult() {
        this.smallIcon = null;
        this.largeIcon = null;
        this.title = null;
        this.body = null;
        this.bigPicture = null;
        this.launchURL = null;
        this.jsonObject = null;
        this.additionalData = null;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }


    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public String getLaunchURL() {
        return launchURL;
    }

    public void setLaunchURL(String launchURL) {
        this.launchURL = launchURL;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(JSONObject additionalData) {
        this.additionalData = additionalData;
    }

    @NonNull
    @Override
    public String toString() {
        return smallIcon+" - "+largeIcon+" - "+title+" - "+body+" - "+bigPicture+" - "+launchURL+" - "+jsonObject+" - "+additionalData;
    }
}
