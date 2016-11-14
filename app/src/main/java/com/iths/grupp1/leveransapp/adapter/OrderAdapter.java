package com.iths.grupp1.leveransapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(View view) {
            super(view);
        }
    }
}
