package com.iths.grupp1.leveransapp.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.util.GenerateOrders;

import java.util.Arrays;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String ORDER_STATUS = "ORDER_STATUS";

    private Order[] orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Used for testing, supposed to get all the orders from the database.
        orders = new Order[1];
        orders[0] = new Order(new Customer(0, "070-12312312", "Storgatan 10"));
        //-------------------------------------------------------------------

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }
}
