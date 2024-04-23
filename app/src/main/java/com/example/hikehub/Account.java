package com.example.hikehub;

import android.media.Image;

import java.util.ArrayList;

public class Account {
    private String email;
    private String password;
    private String name;
    private boolean isMale;
    private int age;
    private double weight;
    private double height;
    private ArrayList<Account> friendList;
    private ArrayList<Chat> chatList;
    private Image profilePhoto;

    public Account(String email, String password, String name, boolean isMale, int age, double height, double weight){
        this.email = email;
        this.password = password;
        this.name = name;
        this.isMale = isMale;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public void addFriend(Account newFriend){
        friendList.add(newFriend);
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

    public void setProfilePhoto(Image profilePhoto) {
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

    public Image getProfilePhoto() {
        return profilePhoto;
    }

    public ArrayList<Account> getFriendList() {
        return friendList;
    }

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
}
