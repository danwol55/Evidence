package com.example.evidence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    List<Evidence>list = new ArrayList<>();

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View dataView = inflater.inflate(R.layout.single_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(dataView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Evidence currentEvidence = list.get(position);
        holder.workDate.setText(currentEvidence.getWorkDay());
        holder.workTime.setText(currentEvidence.getWorkHours());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public void setEvidence(List<Evidence> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }
    public Evidence getEvidenceAt(int position)
    {
        return list.get(position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView workDate;
        TextView workTime;
        MyViewHolder(View item)
        {
            super(item);
            workDate = item.findViewById(R.id.workDate);
            workTime = item.findViewById(R.id.workTime);
        }
    }
}
