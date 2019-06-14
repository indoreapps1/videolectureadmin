package com.example.videolectureadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.activity.LoginActivity;
import com.example.videolectureadmin.activity.MainActivity;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class ChangePassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ChangePassFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ChangePassFragment newInstance(String param1, String param2) {
        ChangePassFragment fragment = new ChangePassFragment();
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

    View view;
    Context context;
    Button btn_reset;
    TextInputEditText edit_new_pass, edit_retypepass;
    String newPass, rePass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        init();
        return view;
    }

    private void init() {
        btn_reset = view.findViewById(R.id.btn_reset);
        edit_new_pass = view.findViewById(R.id.edit_new_pass);
        edit_retypepass = view.findViewById(R.id.edit_retypepass);
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        final int id = sharedPreferences.getInt("id", 0);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Update Password Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ServiceCaller serviceCaller = new ServiceCaller(context);
                    serviceCaller.callUpdatePassService(id, rePass, new IAsyncWorkCompletedCallback() {
                        @Override
                        public void onDone(String workName, boolean isComplete) {
                            if (isComplete) {
                                Toasty.success(context, "Your Password Update Successfuly").show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toasty.error(context, "Password Not Update! Try Again").show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    boolean validate() {
        newPass = edit_new_pass.getText().toString();
        rePass = edit_retypepass.getText().toString();
        if (newPass.length() == 0) {
            edit_new_pass.setError("Enter New Password");
            edit_new_pass.requestFocus();
            return false;
        } else if (rePass.length() == 0) {
            edit_retypepass.setError("Enter Confirm Password");
            edit_retypepass.requestFocus();
            return false;
        } else if (!newPass.equals(rePass)) {
            edit_retypepass.setError("Enter Same Password");
            edit_retypepass.requestFocus();
            return false;
        }
        return true;
    }
}
