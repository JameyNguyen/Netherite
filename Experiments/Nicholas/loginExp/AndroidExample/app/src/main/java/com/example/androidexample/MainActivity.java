package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;
    private TextView messageText2;// define message textview variable
    private Button button1;
    private EditText editText;
    private String newString;
    private Button loginButt;
    private Button signupButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg);      // link to message textview in the Main activity XML
        messageText.setText("Hello World");


        loginButt = findViewById(R.id.loginButt);
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        signupButt = findViewById(R.id.signupButt);
        signupButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });




    }
}