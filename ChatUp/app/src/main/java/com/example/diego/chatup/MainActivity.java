package com.example.diego.chatup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.chatup.Connection.Connection;
import com.example.diego.chatup.Interface.ConnectionListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Connection connection;
    private EditText editText;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        connection = new Connection(new ConnectionListener() {
            @Override
            public void onReceivedMessage(JSONObject object) {
                textView.setText(textView.getText() + "\n" + object.toString());
            }

            @Override
            public void onErrorConnection() {
                Toast.makeText(getApplicationContext(), "Deu pau no server!", Toast.LENGTH_SHORT).show();
            }
        });

        connection.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", "Dollynho");
                    jsonObject.put("message", editText.getText().toString());

                    connection.sendData(jsonObject);

                    editText.setText("");

                }catch (Exception e){

                }
            }
        });
    }
}
