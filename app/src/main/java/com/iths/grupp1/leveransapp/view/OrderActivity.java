package com.iths.grupp1.leveransapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        /*TODO: Get the ID of the Order from the intent, get it from the database and save it to order.
        int id = Integer.parseInt(getIntent().getStringExtra(OrderAdapter.ORDER_ID));
        order = getOrderFromDatabase(id);*/

        orderIdText = (TextView) findViewById(R.id.order_item_orderID_value);
        placedDateText = (TextView) findViewById(R.id.order_activity_placed_value);
        customerNameText = (TextView) findViewById(R.id.order_activity_customer_value);
        deliveryAddressText = (TextView) findViewById(R.id.order_activity_delivery_value);
        phoneNumberText = (TextView) findViewById(R.id.order_activity_phone_value);

        /*Can be used with a valid order object.
        if(order.isDelivered()) {
            findViewById(R.id.ll_delivered).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.GONE);
            deliveredDateText = (TextView) findViewById(R.id.order_activity_delivered_date_value);
            deliveredImage = (ImageView) findViewById(R.id.order_activity_delivered_image);
        } else {
            findViewById(R.id.ll_delivered).setVisibility(View.GONE);
            findViewById(R.id.ll_not_delivered).setVisibility(View.VISIBLE);
            deliveryBtn = (Button) findViewById(R.id.order_activity_delivery_btn);
        }

        orderIdText.setText(order.getOrderNumber());
        placedDateText.setText(order.getOrderPlacementDate() + "");
        customerNameText.setText(order.getCustomer().getCostumerNumber());
        deliveryAddressText.setText(order.getCustomer().getAddress());
        phoneNumberText.setText(order.getCustomer().getCostumerNumber());*/
    }

    public void deliverOrder(View view) {
    }
}
