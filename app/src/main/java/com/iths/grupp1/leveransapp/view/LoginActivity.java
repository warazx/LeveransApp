package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;

public class LoginActivity extends AppCompatActivity {
    private static String correctUsername = "user";
    private static String correctPassword = "pass";
    private TextView textViewUsernameMessage;
    private TextView textViewPasswordMessage;
    private TextView textViewErrorMessage;
    private EditText username;
    private EditText password;
    private static String enteredPassword;
    private static String enteredUsername;
    //Testbutton for Orders.
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewUsernameMessage = (TextView)findViewById(R.id.error_username);
        textViewPasswordMessage = (TextView)findViewById(R.id.error_password);
        textViewErrorMessage = (TextView)findViewById(R.id.error);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //Testbutton for Orders.
        loginBtn = (Button) findViewById(R.id.login_btn);
    }


    public void goToOrders(View view) {
        enteredUsername = username.getText().toString();
        enteredPassword = password.getText().toString();

        evaluateLogInData();
    }

    private void evaluateLogInData(){
        if(correctUsername.equals(enteredUsername) && correctPassword.equals(enteredPassword)){
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        }

        if(enteredUsername.isEmpty()){
            textViewUsernameMessage.setText("Enter username");
        }
        else if(!enteredUsername.equals(correctUsername)){
            textViewUsernameMessage.setText("Not valid username");
        }

        if(enteredPassword.isEmpty()){
            textViewPasswordMessage.setText("Enter password");
        }
        else if(!enteredPassword.equals(correctPassword)){
            textViewPasswordMessage.setText("Not valid password");
        }
    }
}
