package com.example.hikehub;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.ArrayList;
import java.util.Map;

public class Account {
    private String email;
    private String password;
    private String name;
    private boolean isMale;
    private int age;
    private double weight;
    private double height;
    private ArrayList<Account> friendList;
    private ArrayList<Account> friendRequests;
    private ArrayList<Chat> chatList;
    private Drawable profilePhoto;
    private int challengeScore;

    public Account(String email, String password, String name, boolean isMale, int age, double height, double weight){
        this.email = email;
        this.password = password;
        this.name = name;
        this.isMale = isMale;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.friendList = new ArrayList<>();
        this.chatList = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.challengeScore = 0;
    }

    public Account(){}

    public static Account castFromDB(Map<String, Object> data){

        String email = (String) data.get("email");
        String password = (String) data.get("password");
        String name = (String) data.get("name");
        boolean isMale = (boolean) data.get("male");
        int age = Math.toIntExact((Long) data.get("age"));
        double weight = (double) data.get("weight");
        double height = (double) data.get("height");
        ArrayList<Account> friendList = (ArrayList<Account>) data.get("friendList");
        ArrayList<Account> friendRequests = (ArrayList<Account>) data.get("friendRequests");
        ArrayList<Chat> chatList = (ArrayList<Chat>) data.get("chatList");
        Drawable profilePhoto = (Drawable) data.get("profilePhoto");
        int challengeScore =  Math.toIntExact((Long) data.get("challengeScore"));

        Account newAccount = new Account(email, password, name, isMale, age, height, weight);

        newAccount.friendList = friendList;
        newAccount.chatList = chatList;
        newAccount.profilePhoto = profilePhoto;
        newAccount.challengeScore = challengeScore;
        newAccount.friendRequests = friendRequests;

        return newAccount;
    }

    public void removeFriend(Account friend){
        friendList.remove(friend);
    }

    public void startChat(Account receiver){
        chatList.add(new Chat(receiver));
    }


    /**
     *
     * @return index of the chat with the given friend. Returns -1 if there is no existing chat yet
     */
    public int getChatIndexWith(Account friend){
        for (int i = 0; i <= friendList.size(); i++) {
            if (friendList.get(i) == friend) return i;
        }
        return -1;
    }

    public void setProfilePhoto(Drawable profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChallengeScore(int challengeScore) {
        this.challengeScore = challengeScore;
    }

    public Drawable getProfilePhoto() {
        return profilePhoto;
    }

    public ArrayList<Account> getFriendList() {
        return friendList;
    }

    public ArrayList<Account> getFriendRequests() { return friendRequests; }

    public ArrayList<Chat> getChatList() {
        return chatList;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public int getChallengeScore() {
        return challengeScore;
    }
}
