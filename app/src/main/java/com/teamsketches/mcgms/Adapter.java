package com.teamsketches.mcgms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//Recycler.Adapter
//Recycler.ViewHolder
public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private List<show> productList;


    public Adapter(List<show> productList){
        this.productList = productList;
    }

    @NonNull
    @Override
    public Adapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.AdapterViewHolder holder, int position) {

//        holder.textViewcomplaincategory.setText(productList.get(position).getCc());
//        holder.textViewissue.setText(productList.get(position).getIssue());
        holder.textViewcomplaint.setText(productList.get(position).getComplaint());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewcomplaint;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewcomplaincategory = itemView.findViewById(R.id.complaincategory);
//            textViewissue = itemView.findViewById(R.id.issuetype);
            textViewcomplaint = itemView.findViewById(R.id.complaint);
        }
    }
}
