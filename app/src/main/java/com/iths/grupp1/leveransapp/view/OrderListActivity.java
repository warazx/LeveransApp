package com.iths.grupp1.leveransapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.util.GenerateDatabaseObject;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPref;

    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getUndeliveredOrders();
        sharedPref = getSharedPreferences(SettingsActivity.STATUS_USER_SETTINGS, Context.MODE_PRIVATE);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_order_list_menu, menu);

        MenuItem itemSwitch = menu.findItem(R.id.actionbar_switch_item);
        itemSwitch.setActionView(R.layout.use_switch);
        final Switch sw = (Switch) menu.findItem(R.id.actionbar_switch_item).getActionView().findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    historyOrders();
                }else{
                    updateOrders();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.actionbar_settings_item:
                goToSettings();
                //Intent intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                break;
            case R.id.actionbar_add_orders_item:
                addOrders();
                Log.d("TAG","Lägger till ordrar");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds x orders to the database and updates the list. Number of orders is determined
     * by the the ordersPerPage setting in the SettingsActivity.
     */
    public void addOrders() {
        String str = sharedPref.getString("ordersPerPage", "10");
        int amount = Integer.parseInt(str);
        GenerateDatabaseObject.addOrders(this, amount);
        updateOrders();
    }

    /**
     * Gets all undelivered orders from the database and updates the list with them.
     */
    public void updateOrders() {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getUndeliveredOrders();
        swapOrders();
    }

    /**
     * Gets all delivered orders from the database and updates the list with them.
     */
    public void historyOrders() {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getDeliveredOrders();
        swapOrders();
    }

    /**
     * Sends the user to the SettingsActivity.
     */
    public void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void swapOrders() {
        if(orders != null) {
            OrderAdapter newAdapter = new OrderAdapter(orders);
            recyclerView.swapAdapter(newAdapter, true);
        }
    }

}
