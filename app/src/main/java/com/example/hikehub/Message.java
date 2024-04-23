package com.example.hikehub;

public class Message {
    private String timeDateSent;
    private String text;

    private Account sender;

    public Message(String text, String timeDateSent, Account sender){
        this.text = text;
        this.timeDateSent = timeDateSent;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getTimeDateSent() {
        return timeDateSent;
    }

    public Account getSender() {
        return sender;
    }
}
