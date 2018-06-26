/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean signUpModeActive = true;
    TextView tv_changeSignupMode;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_changeSignupMode) {
            Button signupButton = (Button) findViewById(R.id.btn_signup_signup);
            if (signUpModeActive == true) {
                // change to login mode
                signUpModeActive = false;
                signupButton.setText("Login");
                tv_changeSignupMode.setText("Sign Up");
            } else {
                // change to signup mode
                signUpModeActive = true;
                signupButton.setText("Signup");
                tv_changeSignupMode.setText("Login");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_changeSignupMode = (TextView) findViewById(R.id.tv_changeSignupMode);
        tv_changeSignupMode.setOnClickListener(this);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUp(View view) {
        EditText et_signup_username = (EditText) findViewById(R.id.et_signup_username);
        EditText et_signup_password = (EditText) findViewById(R.id.et_signup_password);

        if (et_signup_username.getText().toString().matches("") || et_signup_password.getText().toString().matches("")) {
            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
        } else {
            if (signUpModeActive == true) {
                // signup process
                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(et_signup_username.getText().toString());
                parseUser.setPassword(et_signup_password.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Signup", "Successful");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                // login process
                ParseUser.logInInBackground(et_signup_username.getText().toString(), et_signup_password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null){
                            Log.i("SignUp", "Login successful");
                        }
                        else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}