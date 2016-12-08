package com.iths.grupp1.leveransapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.model.Session;
import com.iths.grupp1.leveransapp.util.GenerateDatabaseObject;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    private static final String PERM_CHECK = "PERM_CHECK";
    private static final int ACCESS_CAMERA = 3;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPref;

    private ArrayList<Order> orders;
    private MenuItem itemSwitch;
    private Switch sw;

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
            activityLogOut();
        }
    }

    private void loadOrders(boolean beenDelivered) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        if(beenDelivered) {
            orders = db.getDeliveredOrders();
            if(getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.activity_order_list_actionbar_label1);
        } else {
            orders = db.getUndeliveredOrders();
            if(getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.activity_order_list_actionbar_label2);
        }
        swapOrders();
    }

    /**
     * Shows the ordershistory or pending orders by users choice of the switch in actionbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_order_list_menu, menu);

        itemSwitch = menu.findItem(R.id.actionbar_switch_item);
        itemSwitch.setActionView(R.layout.use_switch);
        sw = (Switch) menu.findItem(R.id.actionbar_switch_item).getActionView().findViewById(R.id.switch1);
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
                return true;
            case R.id.actionbar_logout_item:
                activityLogOut();
                return true;
            case R.id.actionbar_add_orders_item:
                addOrders();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToQRScanner() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, ACCESS_CAMERA);
                Log.d(PERM_CHECK, String.format("Asking for permission: %s", android.Manifest.permission.CAMERA.toString()));
            }
        } else {
            startQRScanActivity();
        }
    }

    private void startQRScanActivity() {
        Intent intent = new Intent(this, QRScanActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ACCESS_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startQRScanActivity();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Adds x orders to the database and updates the list. Number of orders is determined
     * by the the ordersPerPage setting in the SettingsActivity.
     */
    public void addOrders() {
        String str = sharedPref.getString(SettingsActivity.ORDERS_TO_ADD, SettingsActivity.DEFAULT_ORDERS);
        int amount = Integer.parseInt(str);
        GenerateDatabaseObject.addOrders(this, amount);
        loadOrders(false);
        sw.setChecked(false);
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
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        if(orders != null) beenDeliveredView = orders.size() > 0 && orders.get(0).isDelivered();
        super.onPause();
    }

    @Override
    protected void onResume() {
        loadOrders(beenDeliveredView);
        if(itemSwitch != null) itemSwitch.setChecked(beenDeliveredView);
        super.onResume();
    }

    private void activityLogOut() {
        Session.closeSession(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToQRScan(View view) {
        goToQRScanner();
    }
}
