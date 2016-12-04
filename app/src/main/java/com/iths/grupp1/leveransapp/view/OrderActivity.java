package com.iths.grupp1.leveransapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

/**
 * Show info about one specific order. Has different views depending on if the order
 * has been delivered or not.
 */
public class OrderActivity extends AppCompatActivity {

    private static final int SEND_SMS_PERMISSION = 1;

    private TextView orderIdText;
    private TextView placedDateText;
    private TextView customerNameText;
    private TextView deliveryAddressText;
    private TextView phoneNumberText;
    private TextView deliveredDateText;
    private Button deliveryBtn;

    private Order order;
    private Customer customer;

    private SmsManager smsManager;
    private SharedPreferences sharedPref;
    private final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        order = intent.getExtras().getParcelable(OrderAdapter.EXTRA_ORDER);
        customer = intent.getExtras().getParcelable(OrderAdapter.EXTRA_CUSTOMER);

        orderIdText = (TextView) findViewById(R.id.order_activity_orderid_value);
        placedDateText = (TextView) findViewById(R.id.order_activity_placed_value);
        customerNameText = (TextView) findViewById(R.id.order_activity_name_value);
        deliveryAddressText = (TextView) findViewById(R.id.order_activity_delivery_value);
        phoneNumberText = (TextView) findViewById(R.id.order_activity_phone_value);
        deliveredDateText = (TextView) findViewById(R.id.activity_order_delivered_date_value);
        deliveryBtn = (Button) findViewById(R.id.order_activity_delivery_btn);
        smsManager = SmsManager.getDefault();

        sharedPref = getSharedPreferences(SettingsActivity.STATUS_USER_SETTINGS, Context.MODE_PRIVATE);

        toggleLayout();

        orderIdText.setText(order.getOrderNumber() + "");
        placedDateText.setText(order.getOrderPlacementDate() + "");
        customerNameText.setText(customer.getName());
        deliveryAddressText.setText(customer.getAddress());
        phoneNumberText.setText(customer.getPhoneNumber());
        deliveredDateText.setText(order.getDeliveryDate() + "");
    }

    /**
     * Inflates meny for this activity
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_order_menu, menu);
        return true;
    }

    /**
     * Show Settings when user click on item in actionbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.actionbar_settings_item:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                Log.d(TAG,getString(R.string.log_message));
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Toggles between the views depending on if the order has been delivered or not.
     */
    private void toggleLayout() {
        if(order.isDelivered()) {
            findViewById(R.id.ll_delivered).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ll_delivered).setVisibility(View.GONE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Changes the order to delivered. Updates the database with the location and changes
     * the display to match an delivered order.
     */
    public void deliverOrder(View view) {
        order.setDelivered(true);
        long time = System.currentTimeMillis();
        order.setDeliveryDate(time);
        //TODO: Method to get the coordinates from the gps.
        order.setDeliveryLatitude(111);
        order.setDeliveryLongitude(111);
        //TODO: Change to get the formatted date.
        deliveredDateText.setText(order.getDeliveryDate() + "");
        toggleLayout();

        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        db.updateOrder(order);

        sendSMS();
    }

    /*
     * Sends sms to user when an order is delivered.
     */
    private void sendSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
            }
        } else {
            sendSmsConfirmation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    sendSmsConfirmation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "Need permission to use service", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void sendSmsConfirmation() {
        String phoneNumber = sharedPref.getString(SettingsActivity.PHONE_NUMBER_TO_ADD, "0");
        String message = String.format(getString(R.string.activity_order_sms_message), order.getOrderNumber());

        try {
            smsManager.sendTextMessage(phoneNumber, "", message, null, null);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            Toast.makeText(this, "Invalid phone number, please change in settings.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "SMS could not be sent", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Starts Google Maps Navigation from the user's current location to the current order customer address."
     */
    public void startNavigation(View view) {
        String address = customer.getAddress();
        Uri uri = Uri.parse("google.navigation:q=" + address + "&mode=d");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage(getString(R.string.activity_order_google_maps_package));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
