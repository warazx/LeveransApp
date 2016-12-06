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
 * Custom Adapter for the OrderListActivity.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public static final String EXTRA_ORDER = "EXTRA_ORDER";
    public static final String EXTRA_CUSTOMER = "EXTRA_CUSTOMER";

    private Context context;
    private ArrayList<Order> orders;

    /**
     * Default constructor for the adapter.
     * @param orders ArrayList of Order objects.
     */
    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /*
    Inflates the viewHolder on creation. Uses the order_list_item.xml file and inflates it into the view.
     */
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

    /*
    The ViewHolder for an item in the Order list view. Changes to the layout of an item is done in
    the order_list_item.xml and you bind the variables with the resources here.
     */
    protected class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView customerText;
        private TextView deliveryAddressText;
        private Order order;
        private Customer customer;

        /*
        Constructor for the item. Bind IDs with variables here.
         */
        private OrderViewHolder(View view) {
            super(view);

            context = itemView.getContext();
            customerText = (TextView) itemView.findViewById(R.id.order_item_customer_value);
            deliveryAddressText = (TextView) itemView.findViewById(R.id.order_item_delivery_address_value);

            itemView.setOnClickListener(this);
        }

        /*
        Set the values for an item here.
         */
        private void bindOrder(Order order) {
            OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(context);
            this.order = order;
            customer = db.getCustomer(order.getCustomer());
            customerText.setText(customer.getName());
            deliveryAddressText.setText(customer.formatAddress());
        }

        /*
        This happens if you click on the item.
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra(EXTRA_ORDER, order);
            intent.putExtra(EXTRA_CUSTOMER, customer);
            context.startActivity(intent);
        }
    }
}
