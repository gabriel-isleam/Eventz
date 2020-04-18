package com.example.eventz;

public class Event {
    private String name;
    private String imageUrl;
    private String description;
    private String date;
    private String userId;
    private String location;

    public Event() {

    }
    public Event(String name, String imageUrl, String description, String date, String location, String userID) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
        this.location = location;
        this.userId = userID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
