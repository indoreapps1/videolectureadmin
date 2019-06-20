package com.example.videolectureadmin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.videolectureadmin.R;
import com.example.videolectureadmin.fragments.QAFragment;
import com.example.videolectureadmin.model.Result;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    Context context;
    List<Result> resultList;

    public SubCategoryAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_sub_category, viewGroup, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_txt_video_name.setText(resultList.get(i).getTitle());
        myViewHolder.item_card_sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QAFragment productFragment= QAFragment.newInstance(resultList.get(i).getProductId(),"");
                moveFragment(productFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_video_name, item_txt_video_icon;
        CardView item_card_sub_category;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_video_name=itemView.findViewById(R.id.item_txt_video_name);
//            item_txt_video_icon=itemView.findViewById(R.id.item_txt_video_icon);
            item_card_sub_category=itemView.findViewById(R.id.item_card_sub_category);
        }
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
