package com.iths.grupp1.leveransapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.adapter.OrderAdapter;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.util.GenerateDatabaseObject;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Order[] orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        /*  TODO: Get all undelivered orders from the database.
        Used for testing, supposed to get all the orders from the database.*/
        orders = new Order[2];
        orders[0] = new Order(1);
        orders[1] = new Order(1);
        // ----------------------------------------------------------------

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

    public void addOrders(View view) {
        /*  TODO: Get the number from settings.
            TODO: Move this to the toolbar.*/
        int amount = 10;
        Order[] newOrders = GenerateDatabaseObject.addOrders(amount);
        /*  TODO: Write to the new orders to the database.
        writeToDatabase(newOrders);
         */
    }

    public void updateOrders(View view) {
        /*  TODO: Get all undelivered orders from the database.
            TODO: Move this to the toolbar.
        orders = getUndeliveredOrders();
        */
        adapter.notifyDataSetChanged();
    }

    public void historyOrders(View view) {
        /*  TODO: Get all delivered orders from the database.
            TODO: Move this to the toolbar.
        orders = getDeliveredOrders();
        */
        adapter.notifyDataSetChanged();
    }

}
