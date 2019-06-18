package com.example.videolectureadmin.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.fragments.CategoryFragment;
import com.example.videolectureadmin.fragments.PagerFragment;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.Result;

import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.MYViewHolder> {
    Context context;
    List<Result> resultList;

    public PagerAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public PagerAdapter.MYViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager, viewGroup, false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerAdapter.MYViewHolder myViewHolder, final int i) {
        myViewHolder.video_player.setUp(resultList.get(i).getVideo(), myViewHolder.video_player.SCREEN_LAYOUT_NORMAL);
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
        serviceCaller.callDeletePagerData(resultList.get(i).getId(), new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                dialog.dismiss();
                if (isComplete) {
                    Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT).show();
                    PagerFragment categoryFragment = PagerFragment.newInstance("", "");
                    moveFragment(categoryFragment);
                }

            }
        });
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        return resultList.size();

    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard video_player;
        TextView tv_delete;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            video_player = itemView.findViewById(R.id.video_player);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
