package com.example.vehicledetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText vNumber, vMake, vModel, vVariant;
    Button submit, upload;
    String vehicle_No, vehicle_Make, vehicle_Model,vehicle_Variant;
    Bitmap bitmap;
    String selectedVehicleNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        vNumber = findViewById(R.id.vehicle_number);
        vMake = findViewById(R.id.vehicle_make);
        vModel = findViewById(R.id.vehicle_model);
        vVariant = findViewById(R.id.vehicle_variant);
        submit = findViewById(R.id.submit);
        upload = findViewById(R.id.upload);

        Intent intent = getIntent();
        selectedVehicleNumber = intent.getStringExtra("selectedVehicleNumber");

        dbHelper = new DBHelper(this,"VehiclesInfo",null,1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                vehicle_No = vNumber.getText().toString();
                vehicle_Make = vMake.getText().toString();
                vehicle_Model = vModel.getText().toString();
                vehicle_Variant = vVariant.getText().toString();

                dbHelper.updateRow(selectedVehicleNumber, vehicle_No, vehicle_Model, vehicle_Make,vehicle_Variant,bitmap);
                Intent intent = new Intent(EditActivity.this, list_of_vehicles.class);
                startActivity(intent);


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/jpg"), "SELECT AN IMAGE"), 101);

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