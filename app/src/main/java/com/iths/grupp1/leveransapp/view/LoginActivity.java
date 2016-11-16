package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iths.grupp1.leveransapp.R;

public class LoginActivity extends AppCompatActivity {

    //Testbutton for Orders.
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Testbutton for Orders.
        loginBtn = (Button) findViewById(R.id.login_btn);
    }


    public void goToOrders(View view) {
        Intent intent = new Intent(this, OrderListActivity.class);
        startActivity(intent);
    }
}
