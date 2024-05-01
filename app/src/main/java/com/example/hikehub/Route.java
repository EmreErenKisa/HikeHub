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

    public double caloriesBurned(Account a){
        final double calculationConstant1 = 0.035;
        final double calculationConstant2 = 0.029;
        double calorie = (calculationConstant1* a.getWeight()) + (this.calculateSpeed / a.getHeight())*calculationConstant2*a.getWeight();
        return calorie;
    }
}
