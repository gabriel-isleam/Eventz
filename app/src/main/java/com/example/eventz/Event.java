package com.example.eventz;

public class Event {
    private String name;
    private String imageUrl;
    private String imageDescription;
    private String date;
    private String userId;
    private String location;
    private String tickets_no;

    public Event() {

    }
    public Event(String name, String imageUrl, String description, String date, String location, String userID, String tickets_no) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageDescription = description;
        this.date = date;
        this.location = location;
        this.userId = userID;
        this.tickets_no = tickets_no;
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
        return imageDescription;
    }

    public void setImageDescription(String description) {
        this.imageDescription = description;
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

    public String getTickets_no() {
        return tickets_no;
    }

    public void setTickets_no(String tickets_no) {
        this.tickets_no = tickets_no;
    }
}
