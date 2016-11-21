package com.iths.grupp1.leveransapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iths.grupp1.leveransapp.R;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.view.OrderActivity;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public static final String ORDER_ID = "ORDER_ID";

    private Context context;
    private Order[] orders;

    public OrderAdapter(Order[] orders) {
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
        holder.bindOrder(orders[position]);
    }

    @Override
    public int getItemCount() {
        return orders.length;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView orderIdText;
        public TextView orderTargetText;

        public OrderViewHolder(View view) {
            super(view);

            context = itemView.getContext();
            orderIdText = (TextView) itemView.findViewById(R.id.order_item_orderID_value);
            orderTargetText = (TextView) itemView.findViewById(R.id.order_item_target_value);

            itemView.setOnClickListener(this);
        }

        public void bindOrder(Order order) {
            orderIdText.setText(order.getOrderNumber() + "");
            orderTargetText.setText(order.getCustomer().getAddress());
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra(ORDER_ID, orderIdText.getText());
            context.startActivity(intent);
        }
    }
}
