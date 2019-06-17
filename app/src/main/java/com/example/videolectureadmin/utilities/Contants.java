package com.example.videolectureadmin.utilities;

import android.os.Environment;

/**
 * Created by lalit on 7/25/2017.
 */
public class Contants {
    public static final Boolean IS_DEBUG_LOG = true;

    public static final String LOG_TAG = "vl";
    public static final String APP_NAME = "appName"; // Do NOT change this value; meant for user preference

    public static final String DEFAULT_APPLICATION_NAME = "vl";

    public static final String APP_DIRECTORY = "/E" + DEFAULT_APPLICATION_NAME + "Directory/";
    public static final String DATABASE_NAME = "vl.db";// Environment.getExternalStorageDirectory() +  APP_DIRECTORY + "evergreen.db";

    public static String SERVICE_BASE_URL = "https://loopfusion.in/videolecture/adminApi/";

    public static String outputBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String outputDirectoryLocation = outputBasePath + "/vlUnzipped/";


    public static final int LIST_PAGE_SIZE = 50;
    public static String InternetMessage = " You are Online.";
    public static final String BAD_NETWORK_MESSAGE = "We are trying hard to get latest content but the network seems to be slow. " +
            "You may continue to use app and get latest with in the app itself. ";


    public static final String OFFLINE_MESSAGE = "Oops! You are Offline. Please check your Internet Connection.";
    public static final String SEND_MESSAGE = "OTP Send to Your Phone Number Successfully";
    public static final String ADD_NEW_ADDRESS = "Add New Address Successfully";
    public static final String MESSAGE_FOR_UNREGISTRED_USER = "You Are Not a Registered User!Please Login First..";
    public static final String DoNot_NEW_ADDRESS = "Your Address Do Not Add Successfully";
    public static final String SEND_OTP_MESSAGE = "Your Registration Successfully";
    public static final String DoNot_SEND_OTP_MESSAGE = "OTP NOT Correct.Please Enter Valid OTP ";
    public static final String Dont_SEND_MESSAGE = "OTP Do Not Send Successfully";
    public static final String Dont_GetAddress_MESSAGE = "Some Problem For Geting Address";
    public static final String No_DATA_FOUND_MESSAGE = "No Record Found";
    public static final String Login = "login.php";
    public static final String updatePass = "updatePass.php";
    public static final String GetCategory = "getCategory.php";
    public static final String uploadCategory = "uploadCategory.php";
    public static final String uploadProduct = "uploadProduct.php";
    public static final String deleteCategory = "deleteCategory.php";
    public static final String getAllUsers = "getAllUsers.php";
    public static final String getAllRating = "getAllRating.php";
    public static final String getQuestionAnsTitle = "getQuestionAnsTitle.php";
    public static final String getQuestionAns = "getQuestionAns.php";
    public static final String GetProductData = "getProductData.php";

}
