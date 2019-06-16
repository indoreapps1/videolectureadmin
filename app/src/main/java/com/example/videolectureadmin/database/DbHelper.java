package com.example.videolectureadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.videolectureadmin.model.Result;

import java.util.ArrayList;


/**
 * Created by lalit on 7/25/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Video_Lec";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Video_Lec");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_Video_Lec_TABLE = "CREATE TABLE Video_Lec(id INTEGER,loginId INTEGER, productId INTEGER, question TEXT, answer TEXT,time TEXT)";
        db.execSQL(CREATE_Video_Lec_TABLE);
    }


    //------------basket Order data----------------
    public boolean upsertProductData(Result result) {
        boolean done = false;
        Result data = null;
        if (result.getId() != 0) {
            data = getProductData(result.getId());
            if (data == null) {
                done = insertProductData(result);
                done = true;
            } else {
                done = updateProductData(result);
                done = false;
            }
        }
        return done;
    }

    private void populateProductDataValue(Result ob, ContentValues values) {
        values.put("id", ob.getId());
        values.put("loginId", ob.getLoginId());
        values.put("productId", ob.getProductId());
        values.put("question", ob.getQuestion());
        values.put("answer", ob.getAnswer());
        values.put("time", ob.getTime());
    }

    //GetAll Basket Order
    private void populateProductData(Cursor cursor, Result ob) {
        ob.setId(cursor.getInt(0));
        ob.setLoginId(cursor.getInt(1));
        ob.setProductId(cursor.getString(2));
        ob.setQuestion(cursor.getString(3));
        ob.setAnswer(cursor.getString(4));
        ob.setTime(cursor.getString(5));
    }

    //show  Basket Order list data
    public ArrayList<Result> GetAllProductData() {
        ArrayList<Result> list = new ArrayList<Result>();
        String query = "Select * FROM Video_Lec ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateProductData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //show  Basket Order list data
    public ArrayList<Result> GetAllQuesDataBasedOnQues(String ques) {
        ArrayList<Result> list = new ArrayList<Result>();
        String query = "Select * FROM Video_Lec WHERE question= '" + ques + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateProductData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //show  Basket Order list data
    public ArrayList<Result> GetAllProductDataBasedOnId(String id) {
        ArrayList<Result> list = new ArrayList<Result>();
        String query = "Select * FROM Video_Lec WHERE categId= '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateProductData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //insert Basket Order data
    public boolean insertProductData(Result result) {
        ContentValues values = new ContentValues();
        populateProductDataValue(result, values);
        SQLiteDatabase db = this.getWritableDatabase();

        long i = (long) db.insert("Video_Lec", null, values);
        db.close();
        return i > 0;
    }

    //Basket Order data by id
    public Result getProductData(int id) {
        String query = "Select * FROM Video_Lec WHERE id= " + id + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateProductData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }


    //ProductId,ProductName,BasketId,StoreId,Quantity,Price,OrderTime  MyOrderDataEntity
    //update Basket Order data
    public boolean updateProductData(Result ob) {
        ContentValues values = new ContentValues();
        populateProductDataValue(ob, values);

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("Video_Lec", values, "id = " + ob.getId() + "", null);
        db.close();
        return i > 0;
    }

    // delete Basket Order Data By Store Id ...........
    public boolean deleteProductDataById(String id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "id = " + id + " ";
        db.delete("Video_Lec", query, null);
        db.close();
        return result;
    }

    // delete all  Data
    public boolean deleteAllProductData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Video_Lec", null, null);
        db.close();
        return result;
    }

    public Result getProductData(String ques) {

        String query = "Select * FROM Video_Lec WHERE question= '" + ques + "'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateProductData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }
}