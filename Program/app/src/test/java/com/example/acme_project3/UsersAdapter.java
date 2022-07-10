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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    Context context;
    List<User> usersList;
    RecyclerView rvUsers;
    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowID;
        TextView rowUsername;
        TextView rowPassword;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowID = itemView.findViewById(R.id.item_id);
            rowUsername = itemView.findViewById(R.id.usernameDONOTUSE);
            rowPassword = itemView.findViewById(R.id.passwordDONOTUSE);

        }
    }

    public UsersAdapter(Context context, List<User> usersList, RecyclerView rvUsers){
        this.context = context;
        this.usersList = usersList;
        this.rvUsers = rvUsers;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, viewGroup, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder viewHolder, int i) {
        User user = usersList.get(i);
        viewHolder.rowID.setText(""+user.getId());
        viewHolder.rowUsername.setText(user.getUsername());
        viewHolder.rowPassword.setText(user.getPassword());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvUsers.getChildLayoutPosition(v);
            String item = usersList.get(itemPosition).getUsername();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}