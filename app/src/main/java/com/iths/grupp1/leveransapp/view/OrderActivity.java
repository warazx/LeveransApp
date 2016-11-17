package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.model.Order;

public class OrderActivity extends AppCompatActivity {

    private TextView orderIdText;
    private TextView placedText;
    private TextView customerText;
    private TextView deliveryText;
    private TextView phoneNumberText;
    private Button deliveryBtn;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        /*TODO: Get the ID of the Order from the intent, get it from the database and save it to order.
        int id = Integer.parseInt(getIntent().getStringExtra(OrderAdapter.ORDER_ID));
        order = getOrderFromDatabase(id);*/

        orderIdText = (TextView) findViewById(R.id.order_item_orderID_value);
        placedText = (TextView) findViewById(R.id.order_activity_placed_value);
        customerText = (TextView) findViewById(R.id.order_activity_customer_value);
        deliveryText = (TextView) findViewById(R.id.order_activity_delivery_value);
        phoneNumberText = (TextView) findViewById(R.id.order_activity_phone_value);
        deliveryBtn = (Button) findViewById(R.id.order_activity_delivery_btn);

        /* Can be used with a valid order object.
        orderIdText.setText(order.getOrderNumber());
        placedText.setText(order.getOrderPlacementDate() + "");
        customerText.setText(order.getCustomer().getCostumerNumber());
        deliveryText.setText(order.getCustomer().getAddress());
        phoneNumberText.setText(order.getCustomer().getCostumerNumber());*/
    }

    public void deliverOrder(View view) {
    }
}
