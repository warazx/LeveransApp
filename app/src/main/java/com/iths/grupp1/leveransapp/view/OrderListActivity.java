package com.iths.grupp1.leveransapp.view;

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

    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getUndeliveredOrders();

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

    //TODO: Move this to the toolbar.
    public void addOrders(View view) {
        GenerateDatabaseObject.addOrders(this);
        updateOrders(view);
    }

    //  TODO: Move this to the toolbar.
    public void updateOrders(View view) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getUndeliveredOrders();
        swapOrders();
    }

    //  TODO: Move this to the toolbar.
    public void historyOrders(View view) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(this);
        orders = db.getDeliveredOrders();
        swapOrders();
    }

    private void swapOrders() {
        if(orders != null) {
            OrderAdapter newAdapter = new OrderAdapter(orders);
            recyclerView.swapAdapter(newAdapter, true);
        }
    }
}
