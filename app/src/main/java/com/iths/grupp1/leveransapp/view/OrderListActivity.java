package com.iths.grupp1.leveransapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        sharedPref = getSharedPreferences("userSettings", Context.MODE_PRIVATE);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

    //TODO: Move this to the toolbar.
    /**
     * Adds x orders to the database and updates the list. Number of orders is determined
     * by the the ordersPerPage setting in the SettingsActivity.
     * @param view
     */
    public void addOrders(View view) {
        String str = sharedPref.getString("ordersPerPage", "10");
        int amount = Integer.parseInt(str);
        GenerateDatabaseObject.addOrders(this, amount);
        updateOrders(view);
    }

    //  TODO: Move this to the toolbar.
    /**
     * Gets all undelivered orders from the database and updates the list with them.
     * @param view
     */
    public void updateOrders(View view) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getUndeliveredOrders();
        swapOrders();
    }

    //  TODO: Move this to the toolbar.
    /**
     * Gets all delivered orders from the database and updates the list with them.
     * @param view
     */
    public void historyOrders(View view) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getDeliveredOrders();
        swapOrders();
    }

    //  TODO: Move this to the toolbar.
    /**
     * Sends the user to the SettingsActivity.
     * @param view
     */
    public void goToSettings(View view) {
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
