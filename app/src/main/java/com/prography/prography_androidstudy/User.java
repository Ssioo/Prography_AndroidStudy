package com.prography.prography_androidstudy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    private String email;
    private String pw;


    public User(String email, String pw) {
        this.email = email;
        this.pw = pw;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPw() { return pw; }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
