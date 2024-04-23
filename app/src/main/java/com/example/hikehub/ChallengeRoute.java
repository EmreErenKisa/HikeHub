package com.example.hikehub;

public class ChallengeRoute extends Route{
    private int goalTime;

    public ChallengeRoute(int totalDistance, int goalTime){
        super(totalDistance);
        this.goalTime = goalTime;
    }
    public int calculatePoint(){
        // TODO Calculate earned points
        return 0;
    }
}
