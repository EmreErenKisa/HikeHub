package com.example.hikehub;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> messageList;
    private Account receiver;
    public Chat(Account receiver){
        messageList = new ArrayList<Message>();
        this.receiver = receiver;
    }

    public void newMessageSent(Message newMessage){
        messageList.add(newMessage);
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public Account getReceiver() {
        return receiver;
    }
}
