package com.example.diego.chatup.Interface;


import org.json.JSONObject;

public interface ConnectionListener {

    void onReceivedMessage(JSONObject object);
    void onErrorConnection();

}
