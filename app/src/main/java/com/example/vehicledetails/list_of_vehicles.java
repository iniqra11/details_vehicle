package com.example.vehicledetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class list_of_vehicles extends AppCompatActivity {
    DBHelper dbHelper;
    ArrayList<String> vehicleNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_vehicles);
        final ListView listView = findViewById(R.id.listView);
        Toast.makeText(this, "Click on an item to view its profile", Toast.LENGTH_LONG).show();
        dbHelper = new DBHelper(this, "VehiclesInfo",null,1);
        vehicleNumbers = dbHelper.getVehicleNumber();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vehicleNumbers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedVehicleNumber = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(list_of_vehicles.this, profile_screen.class);
                intent.putExtra("selectedVehicleNumber", selectedVehicleNumber);
                startActivity(intent);
            }
        });


    }
}