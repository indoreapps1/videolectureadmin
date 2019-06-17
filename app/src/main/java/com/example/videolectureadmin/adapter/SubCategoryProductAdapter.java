package com.example.videolectureadmin.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.fragments.CategoryFragment;
import com.example.videolectureadmin.fragments.MainProductFragment;
import com.example.videolectureadmin.fragments.ProductFragment;
import com.example.videolectureadmin.fragments.QAFragment;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.Result;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.MyViewHolder> {
    Context context;
    List<Result> resultList;

    public SubCategoryProductAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public SubCategoryProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_category_product, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryProductAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.item_txt_video_name.setText(resultList.get(i).getTitle());
        myViewHolder.item_card_sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment productFragment = ProductFragment.newInstance(resultList.get(i).getProductId(), "");
                moveFragment(productFragment);
            }
        });
        myViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are You Sure for delete it!");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                deleteCategory(i);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    private void deleteCategory(int i) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Delete Data Wait..");
        dialog.show();
        dialog.setCancelable(false);
        ServiceCaller serviceCaller = new ServiceCaller(context);
        serviceCaller.callDeleteProductData(resultList.get(i).getProductId(), new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                dialog.dismiss();
                if (isComplete) {
                    Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT).show();
                    MainProductFragment categoryFragment = MainProductFragment.newInstance("", "");
                    moveFragment(categoryFragment);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_video_name, tv_delete;
        CardView item_card_sub_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_video_name = itemView.findViewById(R.id.item_txt_video_name);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            item_card_sub_category = itemView.findViewById(R.id.item_card_sub_category);
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
