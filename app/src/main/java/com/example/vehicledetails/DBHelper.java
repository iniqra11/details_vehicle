package com.example.vehicledetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "VehiclesInfo";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE VEHICLE_DETAILS (id INTEGER PRIMARY KEY, vehicle_no TEXT, vehicle_make TEXT, vehicle_model TEXT, vehicle_variant TEXT, vehicle_img BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS VEHICLE_DETAILS");
        onCreate(db);

    }

    public void insertData(String vehicleNumber, String vehicleMake, String vehicleModel, String vehicleVariant, Bitmap vehicleImage) {
        byte[] byteArray;
        SQLiteDatabase db = this.getWritableDatabase();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        vehicleImage.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("vehicle_no", vehicleNumber);
        contentValues.put("vehicle_make", vehicleMake);
        contentValues.put("vehicle_model", vehicleModel);
        contentValues.put("vehicle_variant", vehicleVariant);
        contentValues.put("vehicle_img", byteArray);
        db.insert("VEHICLE_DETAILS", null, contentValues);
        Log.i("Inserted", "Inserted in DB");
    }


    public ArrayList<String> getVehicleNumber() {
        ArrayList<String> vehicleNumbers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM VEHICLE_DETAILS", null);
        int vehicleNumberIndex = c.getColumnIndex("vehicle_no");
        if (c.moveToFirst()) {
            do {
                Log.i("Number", c.getString(vehicleNumberIndex));
                vehicleNumbers.add(c.getString(vehicleNumberIndex));

            } while (c.moveToNext());
        }
        return vehicleNumbers;
    }

    public ArrayList<String> getOtherDetails(String selectedVehicleNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> otherDetails = new ArrayList<String>();
        String selectQuery = "SELECT *  FROM VEHICLE_DETAILS WHERE vehicle_no = " + "\"" + selectedVehicleNumber + "\"";
        Cursor c = db.rawQuery(selectQuery, null);
        int vehicleNumberIndex = c.getColumnIndex("vehicle_no");
        int vehicleMakeIndex = c.getColumnIndex("vehicle_make");
        int vehicleModelIndex = c.getColumnIndex("vehicle_model");
        int vehicleVariantIndex = c.getColumnIndex("vehicle_variant");

        if (c.moveToFirst()) {
           otherDetails.add(c.getString(vehicleNumberIndex));
           otherDetails.add(c.getString(vehicleMakeIndex));
           otherDetails.add(c.getString(vehicleModelIndex));
           otherDetails.add(c.getString(vehicleVariantIndex));

        }
        return otherDetails;
    }
    public Bitmap getImageOfVehicle(String selectedVehicleNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] image;
        String selectQuery = "SELECT *  FROM VEHICLE_DETAILS WHERE vehicle_no = " + "\"" + selectedVehicleNumber + "\"";
        Cursor c = db.rawQuery(selectQuery, null);
        int index = c.getColumnIndex("vehicle_img");
        if (c.moveToFirst()) {
            image = c.getBlob(index);
            Bitmap vehicleImage = BitmapFactory.decodeByteArray(image, 0, image.length);
            return vehicleImage;
        }
        return null;
    }
    public void deleteRow(String selectedVehicleNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM VEHICLE_DETAILS WHERE vehicle_no = " + "\"" + selectedVehicleNumber + "\"");
        db.close();
    }
    public void updateRow(String selectedVehicleNumber, String newNumber, String newMake, String newModel, String newVariant, Bitmap newBitmap)
    {
        byte[] byteArray;
        SQLiteDatabase db = this.getWritableDatabase();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ContentValues newValues = new ContentValues();
        newValues.put("vehicle_no",newNumber);
        newValues.put("vehicle_make", newMake);
        newValues.put("vehicle_model", newModel);
        newValues.put("vehicle_variant", newVariant);
        newValues.put("vehicle_img",byteArray);


        db.update("VEHICLE_DETAILS", newValues, "vehicle_no=?",new String[]{selectedVehicleNumber});
        db.close();
        Log.i("Update", "Updated the DB");

    }
    }


