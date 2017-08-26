package com.example.diego.chatup.Connection;

import android.os.Handler;
import android.os.Message;

import com.example.diego.chatup.Interface.ConnectionListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Thread{

    private final static int port = 1234;
    private Socket socket;
    private boolean isRunning;
    private ConnectionListener connectionListener;

    private Handler handler = new Handler(){ //Trafego de informações entre threads

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            JSONObject jsonObject = (JSONObject) msg.obj;

            if(connectionListener != null){
                connectionListener.onReceivedMessage(jsonObject);
            }

        }
    };

    public Connection(ConnectionListener connectionListener){
        isRunning = true;
        this.connectionListener = connectionListener;

    }

    @Override
    public void run() {
        super.run();

        try {
            socket = new Socket("192.168.2.104", port);

            InputStream inputStream = socket.getInputStream();

            while (isRunning){
                int readBytes = inputStream.available();

                if(readBytes > 0){
                    byte[] arrayBytes = new byte[readBytes];
                    inputStream.read(arrayBytes);

                    String string = new String(arrayBytes);

                    JSONObject jsonObject = new JSONObject(string);

                    Message message = new Message();
                    message.obj = jsonObject;
                    handler.sendMessage(message);

                }

                Thread.sleep(100);
            }


        } catch (Exception e) {
            connectionListener.onErrorConnection();
        }
    }

    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public boolean sendData(JSONObject object){
        if(socket != null && socket.isConnected()){

            try {
                OutputStream outputStream = socket.getOutputStream();

                String message = object.toString();

                byte[] messageByte = message.getBytes();

                outputStream.write(messageByte);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
