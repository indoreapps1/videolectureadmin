package com.example.videolectureadmin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.example.videolectureadmin.utilities.Utility;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edt_username, edt_pass;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        edt_username = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_pass);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//check login Data here.
                if (Utility.isOnline(LoginActivity.this)) {
                    loginData();
                } else {
                    Toasty.error(LoginActivity.this, "No Internet Connection").show();
                }
            }
        });
    }

    private void loginData() {
//        Invalid Information
        if (validation()) {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Verifing Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(LoginActivity.this);
            serviceCaller.callLoginService(username, password, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (isComplete) {
                        if (workName.trim().equalsIgnoreCase("no")) {
                            edt_username.setError("Invalid Information");
                            edt_username.requestFocus();
                        } else {
                            ContentData contentData = new Gson().fromJson(workName, ContentData.class);
                            for (Result result : contentData.getResult()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id", result.getId());
                                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", result.getId());
                                editor.apply();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    } else {
                        Toasty.error(LoginActivity.this, "Login Error Try Again").show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    private boolean validation() {
        username = edt_username.getText().toString().trim();
        password = edt_pass.getText().toString();
        if (username.length() != 10) {
            edt_username.setError("Enter Phone Number");
            edt_username.requestFocus();
            return false;
        } else if (password.length() == 0) {
            edt_pass.setError("Enter Password");
            edt_pass.requestFocus();
            return false;
        }

        return true;
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
