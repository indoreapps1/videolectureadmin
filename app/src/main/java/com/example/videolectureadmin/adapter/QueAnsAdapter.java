package com.example.videolectureadmin.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.database.DbHelper;
import com.example.videolectureadmin.fragments.ProductFragment;
import com.example.videolectureadmin.model.Result;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class QueAnsAdapter extends RecyclerView.Adapter<QueAnsAdapter.MyViewHolder> {

    Context context;
    List<Result> resultList;

    public QueAnsAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public QueAnsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_que_ans, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        DbHelper dbHelper = new DbHelper(context);
        List<Result> resultList1 = dbHelper.GetAllQuesDataBasedOnQues(resultList.get(i).getQuestion());
        Result result = dbHelper.getProductData(resultList.get(i).getQuestion());
        int p = i;
        myViewHolder.item_txt_que.setText("Q." + (p + 1) + " " + resultList.get(i).getQuestion());
        myViewHolder.item_txt_ques_time.setText(result.getTime());
        myViewHolder.recycle_inner_ans.setLayoutManager(new LinearLayoutManager(context));
        if (resultList1 != null && resultList1.size() != 0) {
            myViewHolder.recycle_inner_ans.setAdapter(new AnswerAdater(context, resultList1));
        }
//        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
//        final int loginid = preferences.getInt("id", 0);
//        myViewHolder.item_btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String ansText = myViewHolder.item_edt_ans.getText().toString();
//                if (ansText.length() != 0) {
//                    if (Utility.isOnline(context)) {
//                        final ProgressDialog dialog = new ProgressDialog(context);
//                        dialog.setMessage("Loading Data..");
//                        dialog.show();
//                        ServiceCaller serviceCaller = new ServiceCaller(context);
//                        serviceCaller.callUploadQuesAnsData(loginid, resultList.get(i).getQuestion(), productId, ansText, new IAsyncWorkCompletedCallback() {
//                            @Override
//                            public void onDone(String workName, boolean isComplete) {
//                                dialog.dismiss();
//                                if (isComplete) {
//                                    if (!workName.trim().equalsIgnoreCase("no")) {
//                                        productFragment.showQuesAns();
//                                        Toast.makeText(context, "Your answer uploaded successfull", Toast.LENGTH_SHORT).show();
//                                        myViewHolder.item_edt_ans.setText("");
//                                    } else {
//                                        Toasty.error(context, "Your answer does not upload", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    } else {
//                        Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    myViewHolder.item_edt_ans.setError("Enter Answer Please");
//                    myViewHolder.item_edt_ans.requestFocus();
//
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_que, item_txt_ques_time;
        EditText item_edt_ans;
        RecyclerView recycle_inner_ans;
        Button item_btn_submit;
        String ansText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_que = itemView.findViewById(R.id.item_txt_que);
            item_txt_ques_time = itemView.findViewById(R.id.item_txt_ques_time);
            recycle_inner_ans = itemView.findViewById(R.id.recycle_inner_ans);
        }
    }
}
