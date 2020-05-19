package com.example.eventz;

public class Ticket {

    private String name;
    private String imageUrl;
    private String studentTickets;
    private String adultTickets;

    public Ticket(){

    }

    public Ticket(String name, String imageUrl, String studentTickets, String adultTickets) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.studentTickets = studentTickets;
        this.adultTickets = adultTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStudentTickets() {
        return studentTickets;
    }

    public void setStudentTickets(String studentTickets) {
        this.studentTickets = studentTickets;
    }

    public String getAdultTickets() {
        return adultTickets;
    }

    public void setAdultTickets(String adultTickets) {
        this.adultTickets = adultTickets;
    }
}
