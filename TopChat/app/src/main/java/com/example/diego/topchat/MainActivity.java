package com.example.diego.topchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.diego.topchat.Adapter.AdaptadorMensagens;
import com.example.diego.topchat.Connection.Connection;
import com.example.diego.topchat.Interfaces.ConnectionListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button button;
    private AdaptadorMensagens adaptadorMensagens;
    private Connection connection;

    private ArrayList<JSONObject> mensagens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        mensagens = new ArrayList<>();

        adaptadorMensagens = new AdaptadorMensagens(mensagens);
        listView.setAdapter(adaptadorMensagens);

        connection = new Connection(new ConnectionListener() {
            @Override
            public void onReceivedMessage(JSONObject object) {
                mensagens.add(object);
                adaptadorMensagens.notifyDataSetChanged();
            }

            @Override
            public void onErrorConnection() {

            }
        });

        connection.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", "Diego Rampim");
                    jsonObject.put("message", editText.getText().toString());

                    connection.sendData(jsonObject);

                    jsonObject.put("sender", "0");

                    mensagens.add(jsonObject);

                    adaptadorMensagens.notifyDataSetChanged();

                    editText.setText("");

                }catch (Exception e){
                        e.printStackTrace();
                }
            }
        });
    }
}
