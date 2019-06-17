package com.example.videolectureadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.adapter.CategoryAdapter;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.example.videolectureadmin.utilities.ExpandableTextView;
import com.example.videolectureadmin.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String productId;
    private String mParam2;


    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    Context context;
    RecyclerView category_recycle;
    private List<Result> arrayList;
    JCVideoPlayerStandard video_player;
    TextView txt_title, txt_time;
    ExpandableTextView txt_description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init();
        return view;
    }

    private void init() {
        video_player = view.findViewById(R.id.video_player);
        txt_title = view.findViewById(R.id.txt_title);
        txt_time = view.findViewById(R.id.txt_time);
        txt_description = view.findViewById(R.id.txt_description);
        getProductData();
//        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AddProductFragment addCategoryFragment = AddProductFragment.newInstance("", "");
//                moveFragment(addCategoryFragment);
//            }
//        });
    }

    private void getProductData() {
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data..");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
//            Toast.makeText(context, ""+sub_category_id, Toast.LENGTH_SHORT).show();
            serviceCaller.callAllProductData(productId, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            ContentData myPojo = new Gson().fromJson(workName, ContentData.class);
                            for (Result result : myPojo.getResult()) {
                                video_player.setUp(result.getVideo(), video_player.SCREEN_LAYOUT_NORMAL);
                                video_player.startVideo();
                                txt_title.setText(result.getTitle());
                                txt_description.setText(result.getDescription());
                                txt_time.setText(result.getTime());
                            }

                        } else {
                            Toasty.error(context, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        } else {
            Toasty.info(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
