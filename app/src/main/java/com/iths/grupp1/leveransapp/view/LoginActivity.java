package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.User;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewUsernameMessage;
    private TextView textViewPasswordMessage;
    private EditText username;
    private EditText password;
    private static String enteredPassword;
    private static String enteredUsername;
    private OrderSQLiteOpenHelper users;
    public static String retrievedUsername;
    public static String retrievedPassword;
    //Testbutton for Orders.
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewUsernameMessage = (TextView)findViewById(R.id.error_username);
        textViewPasswordMessage = (TextView)findViewById(R.id.error_password);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        //Testbutton for Orders.
        users = new OrderSQLiteOpenHelper(this);
        loginBtn = (Button) findViewById(R.id.login_btn);

    }


    @Override
    protected void onStart() {
        deleteMessage();
        super.onStart();
    }

    public void goToOrders(View view) {
        enteredUsername = username.getText().toString();
        enteredPassword = password.getText().toString();

        evaluateLogInData();
    }

    private void evaluateLogInData(){

        User user = users.getUser(enteredUsername);

        if(user == null){
            retrievedUsername = "not found";
            retrievedPassword = "not found";
        }else{
            retrievedUsername = user.getUsername();
            retrievedPassword = user.getPassword();
        }

        evaluateUsername();
        evaluatePassword();

        //Intent intent = new Intent(this, OrderListActivity.class);
        //startActivity(intent);
    }

    public void evaluateUsername(){
        if(retrievedUsername.equals(enteredUsername) && retrievedPassword.equals(enteredPassword)){
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        }

        if(enteredUsername.isEmpty()){
            textViewUsernameMessage.setText("Enter username");
        }
        else if(!enteredUsername.equals(retrievedUsername)){
            textViewUsernameMessage.setText("Not valid username");
            textViewPasswordMessage.setText(" ");
        }
    }

    public void evaluatePassword(){
        if(enteredPassword.isEmpty()){
            textViewPasswordMessage.setText("Enter password");
        }
        else if(retrievedPassword.equals("not found")){
            textViewPasswordMessage.setText(" ");
        }
        else if(!enteredPassword.equals(retrievedPassword)){
            textViewPasswordMessage.setText("Not valid password");
        }
    }

    public void deleteMessage(){
        textViewUsernameMessage.setText(" ");
        textViewPasswordMessage.setText(" ");
    }

}
