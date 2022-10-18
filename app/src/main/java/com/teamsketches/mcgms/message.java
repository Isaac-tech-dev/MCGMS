package com.teamsketches.mcgms;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Recycler.Adapter
//Recycler.ViewHolder
public class message extends RecyclerView.Adapter<message.AdapterViewHolder> {

    private List<show2> messagelist;

    public message(List<show2> messagelist){
        this.messagelist = messagelist;
    }

    @NonNull
    @Override
    public message.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_design, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull message.AdapterViewHolder holder, int position) {
        holder.msg.setText(messagelist.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.inbox1);
        }
    }
}
