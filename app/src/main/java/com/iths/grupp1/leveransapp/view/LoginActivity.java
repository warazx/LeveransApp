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
    private TextView textView;
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
        textView = (TextView)findViewById(R.id.error_message);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //Testbutton for Orders.
        loginBtn = (Button) findViewById(R.id.login_btn);
    }


    public void goToOrders(View view) {
        enteredUsername = username.getText().toString();
        enteredPassword = password.getText().toString();

        if(correctUsername.equals(enteredUsername) && correctPassword.equals(enteredPassword)){
            //btn.setEnabled(true);
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        }else{
            textView.setText("Inloggningen misslyckades");
        }

    }
}
