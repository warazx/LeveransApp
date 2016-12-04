package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewUsernameMessage = (TextView)findViewById(R.id.activity_login_username_message);
        textViewPasswordMessage = (TextView)findViewById(R.id.activity_login_password_message);
        username = (EditText)findViewById(R.id.activity_login_username_value);
        password = (EditText)findViewById(R.id.activity_login_password_value);
        users = new OrderSQLiteOpenHelper(this);
    }

    //Tar bort gamla felmmelanden när activityn körs
    @Override
    protected void onStart() {
        deleteMessage();
        super.onStart();
    }

    /**
     * Runs Log in
     * @param view
     */
    public void goToOrders(View view) {
        enteredUsername = username.getText().toString();
        enteredPassword = password.getText().toString();

        evaluateLogInData();
    }

    //Det inmatade lösenordet jämförs mot användarens faktiska lösenord
    private void evaluateLogInData(){

        User user = users.getUser(enteredUsername);

        if(user == null){
            retrievedUsername = getString(R.string.activity_login_evaluate_login_data_error_text);
            retrievedPassword = getString(R.string.activity_login_evaluate_login_data_error_text);
        }else{
            retrievedUsername = user.getUsername();
            retrievedPassword = user.getPassword();
        }

        evaluateUsername();
        evaluatePassword();

    }

    /**
     * Compares entered username to username from database
     */
    public void evaluateUsername(){
        if(retrievedUsername.equals(enteredUsername) && retrievedPassword.equals(enteredPassword)){
            Intent intent = new Intent(this, OrderListActivity.class);
            startActivity(intent);
        }

        if(enteredUsername.isEmpty()){
            textViewUsernameMessage.setText(R.string.activity_login_username_message_text);
        }
        else if(!enteredUsername.equals(retrievedUsername)){
            textViewUsernameMessage.setText(R.string.activity_login_username_message_error_text);
            textViewPasswordMessage.setText(R.string.empty_string);
        }
    }

    /**
     * Compares entered password to password from database
     */
    public void evaluatePassword(){
        if(enteredPassword.isEmpty()){
            textViewPasswordMessage.setText(R.string.activity_login_password_message_text);
        }
        else if(retrievedPassword.equals(getString(R.string.activity_login_evaluate_login_data_error_text))){
            textViewPasswordMessage.setText(R.string.empty_string);
        }
        else if(!enteredPassword.equals(retrievedPassword)){
            textViewPasswordMessage.setText(R.string.activity_login_password_message_error_text);
        }
    }

    protected void deleteMessage(){
        textViewUsernameMessage.setText(R.string.empty_string);
        textViewPasswordMessage.setText(R.string.empty_string);
    }

}
