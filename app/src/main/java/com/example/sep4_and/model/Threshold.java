package com.example.sep4_and.model;

public class Threshold {
    private int id;
    private int min;
    private int max;
    private GreenHouse greenHouse;

    // Constructor
    public Threshold(int id, int min, int max, GreenHouse greenHouse) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.greenHouse = greenHouse;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }
}

