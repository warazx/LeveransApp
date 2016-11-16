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

import java.util.Arrays;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static final String LISTED_ORDERS = "LISTED_ORDERS";

    private Order[] orders = new Order[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /*
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(LISTED_ORDERS);
        orders = Arrays.copyOf(parcelables, parcelables.length, Order[].class);*/
        //TEMP while intent is not finished.
        orders[0] = new Order(new Customer(43243, "083-142455433", "Storgatan 10"));
        orders[1] = new Order(new Customer(23574, "070-333667899", "Rosstigen 7"));
        //-------END TEMP

        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

    }
}
