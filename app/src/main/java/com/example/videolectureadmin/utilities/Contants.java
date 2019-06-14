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
    public static final String otpverifiy = "otpverifiy.php";
    public static final String withdrawotpverifiy = "withdrawotpverifiy.php";
    public static final String reset = "reset.php";
    public static final String getAllPlayLIst = "getAllPlayLIst.php";
    public static final String getParticipated = "getParticipated.php";
    public static final String getAllOngoingList = "getAllOngoingList.php";
    public static final String getAllResultMatchList = "getAllResultMatchList.php";
    public static final String getWinChicken = "getWinChicken.php";
    public static final String getAllSpecialMatch = "getAllSpecialMatch.php";
    public static final String getFullMatch = "getFullMatch.php";
    public static final String getParticipatedMatch = "getParticipatedMatch.php";
    public static final String getUserProfile = "getUserProfile.php";
    public static final String getTopPlayer = "getTopPlayer.php";
    public static final String updateProfile = "updateProfile.php";
    public static final String updatePassword = "updatePassword.php";
    public static final String checkPubgname = "pbzcheckPubgname.php";
    public static final String updateProfileImage = "updateProfileImage.php";
    public static final String getAllTransaction = "getAllTransaction.php";
    public static final String getAllStatics = "getAllStatics.php";
    public static final String sendMoney = "sendMoney.php";
    public static final String addWithdraw = "pbzaddWithdraw.php";
    public static final String checkongoingpop = "checkongoingpop.php";
    public static final String getMyReferrals = "getMyReferrals.php";
    public static final String getReferralAmount = "getReferralAmount.php";
    public static final String update = "update.php";
    public static final String checkSlot = "pbzcheckSlot.php";
    public static final String checkSession = "checkSession.php";
    public static final String TrackOrderStatus = "Order/TrackOrderStatus";
    public static final String GetFavouriteStoreByUser = "Store/GetFavouriteStoreByUser";
    public static final String GetMyAllOrderHistory = "Order/MyAllOrderList";
    public static final String GetAllOrderByUser = "Order/GetAllOrderByUser";
    public static final String AddUpdateFavourite = "Store/AddUpdateFavouriteStoreByUser";
    public static final String UploadProfilePicture = "UploadProfilePicture/UploadProfilePicture";
    public static final String SocialUserLogin = "Login/SocialUserLogin";
    public static final String RemoveFavouriteStoreByUser = "Store/RemoveFavouriteStoreByUser";
    public static final String CreatePaymentOrder = "PaymentGateway/CreatePaymentOrder";
    public static final String PaymentConfirmation = "Payment/PaymentConfirmation";
    public static final String Notification = "Notification/SendNotification";
    public static final String GetAllMenuListByStoreId = "MenuMaster/GetAllMenuListByStoreId";
    public static final String PaymentConfirmforCOD = "Payment/PaymentConfirmforCOD";

}
