package com.prography.prography_androidstudy.src.add_edit.models;

import com.google.gson.annotations.SerializedName;

public class TodoFCMRequest {
    @SerializedName("to") private String token;
    @SerializedName("notification") private Notification notification;

    public class Notification {
        @SerializedName("body") private String body;
        @SerializedName("title") private String title;

        public Notification(String title, String body) {
            this.body = body;
            this.title = title;
        }
    }

    public TodoFCMRequest(String token, String title, String body) {
        this.token = token;
        this.notification = new Notification(title, body);
    }
}
