package com.bootcamp.be_java_hisp_w15_g02.model;

import java.util.ArrayList;
import java.util.List;


public class User {

    private int userId;
    private String userName;

    private List<Follow> followList;
    private List<Follow> followerList;
    private boolean isSeller;


    public User(int userId, String userName, List<Follow> followList, List<Follow> followerList) {
        this.userId = userId;
        this.userName = userName;
        this.followList = followList;
        this.followerList = followerList;
        this.isSeller = false;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Follow> getFollowList() {
        return followList;
    }

    public void setFollowList(List<Follow> followList) {
        this.followList = followList;
    }

    public List<Follow> getFollowerList() {
        return followerList;
    }


    public void setFollowerList(List<Follow> followerList) {
        this.followerList = followerList;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", followList=" + followList +
                ", isSeller=" + isSeller +
                '}';
    }
}
