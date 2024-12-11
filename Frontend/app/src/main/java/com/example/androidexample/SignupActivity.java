package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidexample.FileView.MainActivity;
import com.example.androidexample.Volleys.VolleySingleton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;  // define username edittext variable
    private TextInputEditText passwordEditText;  // define password edittext variable
    private TextInputEditText emailEditText;     // define email edittext variable
    private TextInputEditText confirmEditText;   // define confirm edittext variable
    private MaterialButton loginButton;         // define login button variable
    private MaterialButton signupButton;        // define signup button variable
    private TextView err_msg;

    private TextView msgResponse;
    private static final String URL_JSON_OBJECT = "http://coms-3090-068.class.las.iastate.edu:8080/user/create";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        emailEditText = findViewById(R.id.signup_email_edt);    // link to email edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML
        err_msg = findViewById(R.id.err_msg);

        TextView loginTitle = findViewById(R.id.loginTitle);
        ImageView appLogo = findViewById(R.id.appLogo);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(loginTitle, "alpha", 0f, 1f);
        fadeIn.setDuration(2000);
        fadeIn.start();

        appLogo.setScaleX(0.0f);
        appLogo.setScaleY(0.0f);
        appLogo.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1500).start();

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();

                if (username.length() < 3)
                {
                    err_msg.setText("Username must be at least 4 characters");
                    return;
                }
                //java built in function to check if email is valid
                if (email.indexOf('@') == -1){
                    err_msg.setText("Email must be an email");
                    return;
                }
                if (password.length() < 8){
                    err_msg.setText("Password must be at least 8 characters");
                    return;
                }
                if (password.indexOf('!') == -1){
                    err_msg.setText("Password must contain at least one '!' ");
                    return;
                }
//                if (username.contains(".com") == false){
//                    err_msg.setText("Password must be a valid email ");
//                    return;
//                }

                if (password.equals(confirm)){
                    Log.d("Signup", "Success");
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                    makeJsonObjPost(username, email, password);

                }
                else {
                    Log.d("I fucked up", "Holy shit");
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void makeJsonObjPost(String username, String email, String password) {

        // Create the request body as a JSON object
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjPost = new JsonObjectRequest(
                Request.Method.POST,
                URL_JSON_OBJECT,
                requestBody, // Pass body because its a post request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Signup Success", response.toString());
                        JSONObject resp = response;
                        try {
                            msgResponse.setText(resp.getString("response"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("PASSWORD", password);
                        startActivity(intent);  // go to MainActivity with the key-value data
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                //headers.put("Content-Type", "application/json");
//                return headers;
//            }

            // This function?? i don't remember what the fuck it does
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjPost);
    };
}

