package com.example.videolectureadmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videolectureadmin.R;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    Context context;
    CardView card_category,card_usrs,card_qa,card_rating,card_product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        card_category = view.findViewById(R.id.card_category);
        card_usrs = view.findViewById(R.id.card_usrs);
        card_qa = view.findViewById(R.id.card_qa);
        card_rating = view.findViewById(R.id.card_rating);
        card_product = view.findViewById(R.id.card_product);
        card_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryFragment categoryFragment = CategoryFragment.newInstance("", "");
                moveFragment(categoryFragment);
            }
        });
        card_usrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserListFragment categoryFragment = UserListFragment.newInstance("", "");
                moveFragment(categoryFragment);
            }
        });
        card_qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainQAFragment categoryFragment = MainQAFragment.newInstance("", "");
                moveFragment(categoryFragment);
            }
        });
        card_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingFragment categoryFragment = RatingFragment.newInstance("", "");
                moveFragment(categoryFragment);
            }
        });
        card_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainProductFragment categoryFragment = MainProductFragment.newInstance("", "");
                moveFragment(categoryFragment);
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

}
