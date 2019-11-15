package com.prography.prography_androidstudy.src.main.models;

import com.google.gson.annotations.SerializedName;

public class TodoResponse {
    @SerializedName("owner") private String owner;
    @SerializedName("id") private int id;
    @SerializedName("title") private String title;
    @SerializedName("content") private String content;
    @SerializedName("done") private boolean done;
    @SerializedName("deadline") private long deadline;

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }

    public long getDeadline() {
        return deadline;
    }
}
