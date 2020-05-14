package com.example.eventz;

public class Event {
    private String name;
    private String nameLower;
    private String imageUrl;
    private String imageDescription;
    private String date;
    private String userId;
    private String location;
    private String tickets_no;
    private String student_price;
    private String adult_price;

    public Event() {

    }
    public Event(String name, String nameLower, String imageUrl, String description, String date, String location, String userID, String tickets_no, String student_price, String adult_price) {
        this.name = name;
        this.nameLower = nameLower;
        this.imageUrl = imageUrl;
        this.imageDescription = description;
        this.date = date;
        this.location = location;
        this.userId = userID;
        this.tickets_no = tickets_no;
        this.student_price = student_price;
        this.adult_price = adult_price;
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

    public String getNameLower() {
        return nameLower;
    }

    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
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

    public String getStudent_price() {
        return student_price;
    }

    public void setStudent_price(String student_price) {
        this.student_price = student_price;
    }

    public String getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(String adult_price) {
        this.adult_price = adult_price;
    }
}