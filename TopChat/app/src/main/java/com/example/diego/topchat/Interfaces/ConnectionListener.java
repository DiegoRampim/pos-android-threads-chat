package com.example.diego.topchat.Interfaces;


import org.json.JSONObject;

public interface ConnectionListener {

    void onReceivedMessage(JSONObject object);
    void onErrorConnection();

}
