package com.example.videolectureadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.model.Result;

import java.util.List;

public class AnswerAdater extends RecyclerView.Adapter<AnswerAdater.MYViewHolder> {
    Context context;
    List<Result> resultList;

    public AnswerAdater(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public AnswerAdater.MYViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ans, viewGroup, false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdater.MYViewHolder myViewHolder, int i) {
        if (resultList.get(i).getAnswer() != null && !resultList.get(i).getAnswer().equalsIgnoreCase("")) {
            myViewHolder.item_txt_ans.setText("A." + (i) + " " + resultList.get(i).getAnswer());
            myViewHolder.item_txt_ans_time.setText(resultList.get(i).getTime());
        } else {
            myViewHolder.item_txt_ans.setVisibility(View.GONE);
            myViewHolder.item_txt_ans_time.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();

    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_ans, item_txt_ans_time;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_ans = itemView.findViewById(R.id.item_txt_ans);
            item_txt_ans_time = itemView.findViewById(R.id.item_txt_ans_time);
        }
    }
}
