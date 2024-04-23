package com.example.hikehub;

public class Route {
    private int totalDistance;
    private int distanceTravelled;
    private int timePassedInSeconds;

    public Route(int totalDistance){
        this.totalDistance = totalDistance;
        distanceTravelled = 0;
        timePassedInSeconds = 0;
    }

    public double calculateSpeed(){
        return distanceTravelled / timePassedInSeconds;
    }

    public int caloriesBurned(){
        // TODO Calculate how many calories burned according to distance, speed, gender etc.
        return 0;
    }
}
