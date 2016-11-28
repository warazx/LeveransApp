package com.iths.grupp1.leveransapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

public class OrderActivity extends AppCompatActivity {

    private TextView orderIdText;
    private TextView placedDateText;
    private TextView customerNameText;
    private TextView deliveryAddressText;
    private TextView phoneNumberText;
    private TextView deliveredDateText;
    private ImageView deliveredImage;
    private Button deliveryBtn;

    private Order order;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        order = intent.getExtras().getParcelable(OrderAdapter.SINGLE_ORDER);
        customer = intent.getExtras().getParcelable(OrderAdapter.SINGLE_CUSTOMER);

        orderIdText = (TextView) findViewById(R.id.order_activity_orderid_value);
        placedDateText = (TextView) findViewById(R.id.order_activity_placed_value);
        customerNameText = (TextView) findViewById(R.id.order_activity_name_value);
        deliveryAddressText = (TextView) findViewById(R.id.order_activity_delivery_value);
        phoneNumberText = (TextView) findViewById(R.id.order_activity_phone_value);
        deliveredDateText = (TextView) findViewById(R.id.order_activity_delivered_date_value);
        deliveredImage = (ImageView) findViewById(R.id.order_activity_delivered_image);
        deliveryBtn = (Button) findViewById(R.id.order_activity_delivery_btn);

        toggleLayout();

        orderIdText.setText(order.getOrderNumber() + "");
        placedDateText.setText(order.getOrderPlacementDate() + "");
        customerNameText.setText(customer.getName());
        deliveryAddressText.setText(customer.getAddress());
        phoneNumberText.setText(customer.getPhoneNumber());
        deliveredDateText.setText(order.getDeliveryDate() + "");
    }

    private void toggleLayout() {
        if(order.isDelivered()) {
            findViewById(R.id.ll_delivered).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ll_delivered).setVisibility(View.GONE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.VISIBLE);
        }
    }

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

        sendSms();
    }

    /**
     * Sends sms to user when an order is delivered.
     */
    private void sendSms() {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        String phoneNumber = sharedPref.getString("phoneNumber", "10");
        String message = "Order nr" + orderIdText + " has been delivered.";
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(phoneNumber, null, message, null, null);

    }
}
