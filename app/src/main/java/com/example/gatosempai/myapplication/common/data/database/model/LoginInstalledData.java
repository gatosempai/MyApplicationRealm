package com.example.gatosempai.myapplication.common.data.database.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LoginInstalledData extends RealmObject implements BaseModel {

    @PrimaryKey
    private long id;

    private String userName;
    private String userNumber;

    public LoginInstalledData(){
        this.setUserName(null);
        this.setUserNumber(null);
    }

    public LoginInstalledData(String userNumber, String userName){
        this.setUserNumber(userNumber);
        this.setUserName(userName);
    }

    @Override
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserNumber(){
        return userNumber;
    }

    public void setUserNumber(String userNumber){
        this.userNumber = userNumber;
    }

    @Override
    public String toString(){
        return "UserName: " + this.userName +
                "\nUserNumber: " + this.userNumber;
    }
}
