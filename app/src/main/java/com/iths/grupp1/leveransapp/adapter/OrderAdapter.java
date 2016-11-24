package com.iths.grupp1.leveransapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.view.OrderActivity;

import java.util.ArrayList;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public static final String SINGLE_ORDER = "ORDER_ID";
    public static final String SINGLE_CUSTOMER = "SINGLE_CUSTOMER";

    private Context context;
    private ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.bindOrder(orders.get(position));
    }

    @Override
    public int getItemCount() {
        if(orders == null) {
            return 0;
        } else return orders.size();
    }

    protected class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderIdText;
        private TextView orderTargetText;
        private Order order;
        private Customer customer;

        private OrderViewHolder(View view) {
            super(view);

            context = itemView.getContext();
            orderIdText = (TextView) itemView.findViewById(R.id.order_item_orderID_value);
            orderTargetText = (TextView) itemView.findViewById(R.id.order_item_target_value);

            itemView.setOnClickListener(this);
        }

        private void bindOrder(Order order) {
            OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(context);
            this.order = order;
            customer = db.getCustomer(order.getCustomer());
            orderIdText.setText(order.getOrderNumber() + "");
            orderTargetText.setText(customer.getAddress());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra(SINGLE_ORDER, order);
            intent.putExtra(SINGLE_CUSTOMER, customer);
            context.startActivity(intent);
        }
    }
}
