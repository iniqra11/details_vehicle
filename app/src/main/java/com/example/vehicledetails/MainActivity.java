package com.example.vehicledetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    Button upload;
    Button submit;
    Button viewList;

    EditText vehicleNumber;
    EditText vehicleMake;
    EditText vehicleModel;
    EditText vehicleVariant;

    Bitmap bitmap;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vehicleNumber = findViewById(R.id.vehicle_number);
        vehicleMake = findViewById(R.id.vehicle_make);
        vehicleModel = findViewById(R.id.vehicle_model);
        vehicleVariant = findViewById(R.id.vehicle_variant);

        upload = findViewById(R.id.upload);
        submit = findViewById(R.id.submit);
        viewList = findViewById(R.id.view_list);

        dbHelper = new DBHelper(this, "VehiclesInfo",null, 1);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/jpg"), "SELECT AN IMAGE"), 101);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String vehicle_No, vehicle_Make, vehicle_Model,vehicle_Variant;
                vehicle_No = vehicleNumber.getText().toString();
                vehicle_Make = vehicleMake.getText().toString();
                vehicle_Model = vehicleModel.getText().toString();
                vehicle_Variant = vehicleVariant.getText().toString();
                dbHelper.insertData(vehicle_No,vehicle_Make,vehicle_Model,vehicle_Variant,bitmap);
            }
        });



        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,list_of_vehicles.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}