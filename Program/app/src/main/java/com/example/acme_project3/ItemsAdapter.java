package com.example.acme_project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    Context context;
    List<Item> itemsList;
    RecyclerView rvInventory;
    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowProductName;
        TextView rowItemID;
        TextView rowListID;
        TextView rowDepartment;
        TextView rowDetails;
        TextView rowQuantity;
        TextView rowCost;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowProductName = itemView.findViewById(R.id.productname);
            rowItemID = itemView.findViewById(R.id.item_id);
            rowListID = itemView.findViewById(R.id.item_listid);
            rowDepartment = itemView.findViewById(R.id.department);
            rowDetails = itemView.findViewById(R.id.details);
            rowQuantity = itemView.findViewById(R.id.quantity);
            rowCost = itemView.findViewById(R.id.cost);
        }
    }

    public ItemsAdapter(Context context, List<Item> itemsList, RecyclerView rvInventory){
        this.context = context;
        this.itemsList = itemsList;
        this.rvInventory = rvInventory;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_inventory_item, viewGroup, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder viewHolder, int i) {
        Item item = itemsList.get(i);
        viewHolder.rowProductName.setText(item.getProductName());
        viewHolder.rowItemID.setText(""+item.getID());
        viewHolder.rowListID.setText(""+item.getListID());
        viewHolder.rowDepartment.setText(""+item.getDepartment());
        viewHolder.rowDetails.setText(""+item.getDetails());
        viewHolder.rowQuantity.setText(""+item.getQuantity());
        viewHolder.rowCost.setText(""+item.getCost());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvInventory.getChildLayoutPosition(v);
            String item = itemsList.get(itemPosition).getProductName();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}