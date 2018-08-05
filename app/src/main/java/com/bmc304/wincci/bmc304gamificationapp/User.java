package com.bmc304.wincci.bmc304gamificationapp;

/**
 * Created by wincci on 11/23/2017.
 */

public class User {
    String username;
    String userID;
    String email;
    String userType;
    double score;
    String status;
    String image;
    double latitude;
    double longitude;

    public User() {
    }

    public User(String username, String userID, String email, String userType, double score, String
            status, String image, double latitude, double longitude) {

        this.username = username;
        this.userID = userID;
        this.email = email;
        this.userType = userType;
        this.score = score;
        this.status = status;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
