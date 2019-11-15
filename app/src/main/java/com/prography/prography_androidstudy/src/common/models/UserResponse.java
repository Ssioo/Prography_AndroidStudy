package com.prography.prography_androidstudy.src.common.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("id") private int uniqueId;
    @SerializedName("username") private String username;

    public int getUniqueId() {
        return uniqueId;
    }

    public String getUsername() {
        return username;
    }
}
