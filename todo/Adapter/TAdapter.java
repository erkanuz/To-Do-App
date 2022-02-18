package com.example.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Buttons.AddNewTask;
import com.example.todo.Model.TModel;
import com.example.todo.R;
import com.example.todo.Second;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TAdapter extends RecyclerView.Adapter<TAdapter.MyViewHolder> {

    private List<TModel> tlist;
    private Second activity;
    private FirebaseFirestore firestore;

    public TAdapter(Second second , List<TModel> tlist){
        this.tlist = tlist;
        activity = second;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task , parent , false);
        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    public void deleteTask(int position){
        TModel tModel = tlist.get(position);
        firestore.collection("task").document(tModel.TId).delete();
        tlist.remove(position);
        notifyItemChanged(position);
    }

    public Context getContext(){
        return activity;
    }

    public void editTask(int position){
        TModel tModel = tlist.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , tModel.getTask());
        bundle.putString("due" , tModel.getDue());
        bundle.putString("id" , tModel.TId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager() , addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TModel tModel = tlist.get(position);

        holder.mCheckBox.setText(tModel.getTask());
        holder.mDueDateTv.setText("Due on " + tModel.getDue());
        holder.mCheckBox.setChecked(toBoolean(tModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firestore.collection("task").document(tModel.TId).update("status" , 1);
                } else {
                    firestore.collection("task").document(tModel.TId).update("status" , 0);
                }
            }
        });
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return tlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
