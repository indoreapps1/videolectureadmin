package com.example.videolectureadmin.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.videolectureadmin.R;
import com.example.videolectureadmin.framework.IAsyncWorkCompletedCallback;
import com.example.videolectureadmin.framework.ServiceCaller;
import com.example.videolectureadmin.model.ContentData;
import com.example.videolectureadmin.model.Result;
import com.example.videolectureadmin.utilities.Upload;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    Context context;
    Button btn_submit, btn_choose;
    TextInputEditText edt_catename, edt_des;
    ImageView imageView;
    JCVideoPlayerStandard video_player;
    private int SELECT_VIDEO = 3;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    String selectedPath;
    Spinner spinner;
    String category;
    List<String> arrayList;

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
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_addproduct, container, false);
        init();
        return view;
    }

    private void init() {
        arrayList = new ArrayList<>();
        //Requesting storage permission
        requestStoragePermission();
        video_player = view.findViewById(R.id.video_player);
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_choose = view.findViewById(R.id.btn_choose);
        edt_catename = view.findViewById(R.id.edt_catename);
        edt_des = view.findViewById(R.id.edt_des);
        imageView = view.findViewById(R.id.imageView);
        spinner = view.findViewById(R.id.spinner);
        getCategory();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname = edt_catename.getText().toString();
                String des = edt_des.getText().toString();
                if (cname.length() == 0) {
                    edt_catename.setError("Enter Video Title");
                    edt_catename.requestFocus();
                } else if (des.length() == 0) {
                    edt_des.setError("Enter Video Description");
                    edt_des.requestFocus();
                } else {
                    if (category != null) {
                        if (selectedPath != null) {
                            uploadVideo();
                        } else {
                            Toasty.error(context, "Please Select Video").show();
                        }
                    } else {
                        Toasty.error(context, "Please Select Category").show();

                    }
                }
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString(); //this is your selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCategory() {
        ServiceCaller serviceCaller = new ServiceCaller(context);
        serviceCaller.callCategoryData(new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                if (isComplete) {
                    ContentData myPojo = new Gson().fromJson(workName, ContentData.class);
                    for (Result result : myPojo.getResult()) {
                        arrayList.addAll(Arrays.asList(result.getCategoryName()));
                    }
                    if (arrayList != null) {
//                        Toast.makeText(context, ""+arrayList.size(), Toast.LENGTH_SHORT).show();
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
                        spinner.setAdapter(stringArrayAdapter);
                    } else {
                        Toasty.error(context, "Any Category Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                video_player.setUp(selectedPath, video_player.SCREEN_LAYOUT_NORMAL);
//                video_player.startVideo();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(context, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(context, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(getActivity(), "Uploading Video", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                uploadData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(selectedPath);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

    private void uploadData(String s) {
//            String uploadImage = getStringImage(bitmap);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Adding Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ServiceCaller serviceCaller = new ServiceCaller(context);
        serviceCaller.callAddProductData(category, edt_catename.getText().toString(), edt_des.getText().toString(), s, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                if (isComplete) {
                    Toasty.success(context, "Add Product Successfully").show();
                    getFragmentManager().popBackStack();
                }
                progressDialog.dismiss();
            }
        });

    }
}
