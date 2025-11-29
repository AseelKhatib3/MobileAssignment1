package com.example.assignment1;

import java.io.Serializable;

public class Trip implements Serializable {

    private String origin;
    private String destination;
    private String date;
    private String budget;
    private String type;
    private String notes;

    private int duration;
    private int travelers;
    private String weather;
    private String accommodation;
    private String transport;

    private boolean breakfast, lunch, dinner;
    private boolean passport, charger, clothes, medicine, camera;

    private int imageRes;
    private String imageUri;

    public Trip(String origin, String destination, String date, String budget,
                String type, String notes, int imageRes) {

        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
        this.type = type;
        this.notes = notes;

        this.imageRes = imageRes;
        this.imageUri = null;

        this.duration = 0;
        this.travelers = 1;
        this.weather = "";
        this.accommodation = "";
        this.transport = "";
    }

    public Trip(String origin, String destination, String date, String budget,
                String type, String notes, int imageRes, String imageUri,
                int duration, int travelers, String weather,
                String accommodation, String transport,
                boolean breakfast, boolean lunch, boolean dinner,
                boolean passport, boolean charger, boolean clothes,
                boolean medicine, boolean camera) {

        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
        this.type = type;
        this.notes = notes;

        this.imageRes = imageRes;
        this.imageUri = imageUri;

        this.duration = duration;
        this.travelers = travelers;
        this.weather = weather;
        this.accommodation = accommodation;
        this.transport = transport;

        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;

        this.passport = passport;
        this.charger = charger;
        this.clothes = clothes;
        this.medicine = medicine;
        this.camera = camera;
    }

    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public String getBudget() { return budget; }
    public String getType() { return type; }
    public String getNotes() { return notes; }

    public int getDuration() { return duration; }
    public int getTravelers() { return travelers; }
    public String getWeather() { return weather; }
    public String getAccommodation() { return accommodation; }
    public String getTransport() { return transport; }

    public boolean isBreakfast() { return breakfast; }
    public boolean isLunch() { return lunch; }
    public boolean isDinner() { return dinner; }

    public boolean isPassport() { return passport; }
    public boolean isCharger() { return charger; }
    public boolean isClothes() { return clothes; }
    public boolean isMedicine() { return medicine; }
    public boolean isCamera() { return camera; }

    public int getImageRes() { return imageRes; }
    public String getImageUri() { return imageUri; }

    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDate(String date) { this.date = date; }
    public void setBudget(String budget) { this.budget = budget; }
    public void setType(String type) { this.type = type; }
    public void setNotes(String notes) { this.notes = notes; }

    public void setDuration(int duration) { this.duration = duration; }
    public void setTravelers(int travelers) { this.travelers = travelers; }
    public void setWeather(String weather) { this.weather = weather; }
    public void setAccommodation(String accommodation) { this.accommodation = accommodation; }
    public void setTransport(String transport) { this.transport = transport; }

    public void setBreakfast(boolean breakfast) { this.breakfast = breakfast; }
    public void setLunch(boolean lunch) { this.lunch = lunch; }
    public void setDinner(boolean dinner) { this.dinner = dinner; }

    public void setPassport(boolean passport) { this.passport = passport; }
    public void setCharger(boolean charger) { this.charger = charger; }
    public void setClothes(boolean clothes) { this.clothes = clothes; }
    public void setMedicine(boolean medicine) { this.medicine = medicine; }
    public void setCamera(boolean camera) { this.camera = camera; }

    public void setImageRes(int imageRes) { this.imageRes = imageRes; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
