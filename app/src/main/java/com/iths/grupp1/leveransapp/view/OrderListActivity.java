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
import com.iths.grupp1.leveransapp.model.Session;
import com.iths.grupp1.leveransapp.util.GenerateDatabaseObject;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPref;

    private ArrayList<Order> orders;
    private MenuItem itemSwitch;

    private boolean beenDeliveredView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        loadOrders(false);
        sharedPref = getSharedPreferences(SettingsActivity.STATUS_USER_SETTINGS, Context.MODE_PRIVATE);

        beenDeliveredView = false;

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (! Session.isSessionValid(this)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void loadOrders(boolean beenDelivered) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        if(beenDelivered) {
            orders = db.getDeliveredOrders();
        } else {
            orders = db.getUndeliveredOrders();
        }

        //if(itemSwitch != null) aSwitch.setChecked(beenDelivered);
        swapOrders();
    }

    /**
     * Shows the ordershistory or pending orders by users choice of the switch in actionbar
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_order_list_menu, menu);

        itemSwitch = menu.findItem(R.id.actionbar_switch_item);
        itemSwitch.setActionView(R.layout.use_switch);
        final Switch sw = (Switch) menu.findItem(R.id.actionbar_switch_item).getActionView().findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loadOrders(true);
                }else{
                    loadOrders(false);
                }
            }
        });
        return true;
    }

    /**
     *  runs activity depending on users choice in actionbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.actionbar_settings_item:
                goToSettings();
                break;
            case R.id.actionbar_add_orders_item:
                addOrders();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO: After you add orders and change to the undelivered view, the switch does not update and is still toggled ON.
    /**
     * Adds x orders to the database and updates the list. Number of orders is determined
     * by the the ordersPerPage setting in the SettingsActivity.
     */
    public void addOrders() {
        String str = sharedPref.getString(SettingsActivity.ORDERS_TO_ADD, SettingsActivity.DEFAULT_ORDERS);
        int amount = Integer.parseInt(str);
        GenerateDatabaseObject.addOrders(this, amount);
        loadOrders(false);
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

    @Override
    protected void onPause() {
        beenDeliveredView = orders.size() > 0 && orders.get(0).isDelivered();
        super.onPause();
    }

    @Override
    protected void onResume() {
        loadOrders(beenDeliveredView);
        if(itemSwitch != null) itemSwitch.setChecked(beenDeliveredView);
        super.onResume();
    }
}
