package com.example.vehicledetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class profile_screen extends AppCompatActivity {

    TextView vehicle_Number, vehicle_Make, vehicle_Model, vehicle_Variant;
    ImageView vehicle_Image;
    private DBHelper dbHelper;
    ArrayList<String> otherDetails;
    Bitmap vehicleImage;
    String vehicleNumber, vehicleMake, vehicleModel, vehicleVariant;
    Button removeVehicle;
    Button editVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        vehicle_Number = findViewById(R.id.number_text);
        vehicle_Make = findViewById(R.id.make_text);
        vehicle_Model = findViewById(R.id.model_text);
        vehicle_Variant = findViewById(R.id.variant_text);
        vehicle_Image  = findViewById(R.id.image_vehicle);
        removeVehicle = findViewById(R.id.removeVehicle);
        editVehicle = findViewById(R.id.editVehicle);


        dbHelper = new DBHelper(this,"VehiclesInfo",null,1);
        otherDetails = new ArrayList<String>();

        Intent intent = getIntent();
        final String selectedVehicleNumber = intent.getStringExtra("selectedVehicleNumber");

        otherDetails =  dbHelper.getOtherDetails(selectedVehicleNumber);
        vehicleImage = dbHelper.getImageOfVehicle(selectedVehicleNumber);


        vehicleNumber = otherDetails.get(0);
        vehicleMake = otherDetails.get(1);
        vehicleModel = otherDetails.get(2);
        vehicleVariant = otherDetails.get(3);
        vehicle_Image.setImageBitmap(vehicleImage);

        vehicle_Number.setText(vehicle_Number.getText().toString() + vehicleNumber);
        vehicle_Model.setText(vehicle_Model.getText().toString() + vehicleModel);
        vehicle_Make.setText(vehicle_Make.getText().toString() + vehicleMake);
        vehicle_Variant.setText(vehicle_Variant.getText().toString() + vehicleVariant);

        removeVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dbHelper.deleteRow(selectedVehicleNumber);
                Toast.makeText(profile_screen.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profile_screen.this, MainActivity.class);
                startActivity(intent);
            }
        });

          editVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(profile_screen.this, EditActivity.class);
                intent.putExtra("selectedVehicleNumber", selectedVehicleNumber);
                startActivity(intent);
            }
        });

    }
}