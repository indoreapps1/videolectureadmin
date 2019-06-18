package com.example.videolectureadmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.adapter.PagerAdapter;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class PagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PagerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(String param1, String param2) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Context context;
    View view;
    RecyclerView recycleView;
    private List<Result> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_pager, container, false);
        init();
        return view;
    }

    private void init() {
        arrayList = new ArrayList<>();
        recycleView = view.findViewById(R.id.recycleView);
        getPagerData();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPagerFragment addCategoryFragment = AddPagerFragment.newInstance("", "");
                moveFragment(addCategoryFragment);
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


    private void getPagerData() {
        ServiceCaller serviceCaller = new ServiceCaller(context);
        serviceCaller.callPagerData(new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                if (isComplete) {
                    if (workName.trim().equalsIgnoreCase("no")) {
                        Toasty.error(context, "no data found").show();
                    } else {
                        ContentData myPojo = new Gson().fromJson(workName, ContentData.class);
                        for (Result result : myPojo.getResult()) {
                            arrayList.addAll(Arrays.asList(result));
                        }
                        if (arrayList != null) {
                            PagerAdapter categoryAdapter = new PagerAdapter(context, arrayList);
                            recycleView.setLayoutManager(new LinearLayoutManager(context));
                            recycleView.setAdapter(categoryAdapter);
                        }
                    }
                }
            }
        });
    }
}
