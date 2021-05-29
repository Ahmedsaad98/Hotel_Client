package com.ahmedalraziki.graduation_client.Classes;

import com.google.firebase.database.DatabaseReference;

public class Room {
    String number;
    String floor ;
    int availability;
    DatabaseReference roomRef;

    public Room(String number, String floor, int availability, DatabaseReference roomRef) {
        this.number = number;
        this.floor = floor;
        this.availability = availability;
        this.roomRef = roomRef;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public DatabaseReference getRoomRef() {
        return roomRef;
    }

    public void setRoomRef(DatabaseReference roomRef) {
        this.roomRef = roomRef;
    }
}
