package com.example.videolectureadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.adapter.QueAnsAdapter;
import com.example.videolectureadmin.adapter.UsersListAdapter;
import com.example.videolectureadmin.database.DbHelper;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.example.videolectureadmin.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class QAFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String productId;
    private String mParam2;


    public QAFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QAFragment newInstance(String param1, String param2) {
        QAFragment fragment = new QAFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    Context context;
    RecyclerView recycleView;
    private List<Result> arrayList;

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
        view = inflater.inflate(R.layout.fragment_qa, container, false);
        init();
        return view;
    }

    private void init() {
        recycleView = view.findViewById(R.id.recycleView);
        gtUsersList();
    }

    private void gtUsersList() {
        arrayList = new ArrayList<>();
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data Wait..");
            dialog.show();
            dialog.setCancelable(false);
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callAllQusAnsData(productId,new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (workName.trim().equalsIgnoreCase("no")) {
                            Toasty.error(context, "Any Data Not Found", Toast.LENGTH_SHORT).show();
                        } else {
                            DbHelper dbHelper = new DbHelper(context);
                            ContentData myPojo = new Gson().fromJson(workName, ContentData.class);
                            for (Result result : myPojo.getResult()) {
                                arrayList.addAll(Arrays.asList(result));
                            }
                            if (arrayList != null) {
                                for (Result result : arrayList) {
                                    dbHelper.upsertProductData(result);
                                }
                                sortProductData(arrayList);

                            } else {
                                Toasty.error(context, "Any Data Not Found", Toast.LENGTH_SHORT).show();
                            }
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

    private void sortProductData(List<Result> resultList) {
        List<Result> newList = new ArrayList<Result>();
        for (Result c : resultList) {
            if (!categoryDataExist(newList, c.getQuestion())) {
                newList.add(c);
            }
        }
        Collections.reverse(newList);
        QueAnsAdapter categoryAdapter = new QueAnsAdapter(context, newList);
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setAdapter(categoryAdapter);
    }
    private boolean categoryDataExist(List<Result> newList, String name) {
        for (Result c : newList) {
            if (c.getQuestion().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
