package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class campaignActivity extends Fragment {
    EditText campName,locationCamp,timeCamp,time1Camp,dateCamp,date1Camp;
    TextInputLayout name,location,time0,time1,date0,date1;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    int numOfProducts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_campaign, container, false);
    }
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initializeUI();
        getView().findViewById(R.id.submit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!validateCampName() | !validateCampLocation() | !validateCamptime0() | !validateCamptime1() | !validateCampdate() | !validateCampdate1()){
                    return;
                }
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                String uid = user.getUid();
                String id= UUID.randomUUID().toString();
                Map<String,Object> data = new HashMap<>();
                String Input = campName.getText().toString().trim();
                String Input1 = locationCamp.getText().toString().trim();
                String Input2 = timeCamp.getText().toString().trim();
                String Input3 = time1Camp.getText().toString().trim();
                String Input4 = dateCamp.getText().toString().trim();
                String Input5 = date1Camp.getText().toString().trim();
                data.put("Hospital_name",Input);
                data.put("location",Input1);
                data.put("starttime",Input2);
                data.put("endtime",Input3);
                data.put("startDate",Input4);
                data.put("endDate",Input5);
                data.put("Type","campaign");
                FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        numOfProducts = (int) dataSnapshot.getChildrenCount();
                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });
                ++numOfProducts;
                data.put("id",Integer.toString(numOfProducts));
                FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").child(Integer.toString(numOfProducts)).setValue(data)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "on Failuer", Toast.LENGTH_SHORT).show();
                                Log.d("error", e.getLocalizedMessage());
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Your request is added", Toast.LENGTH_SHORT).show();


                            }
                        });
            }
        });


    }
    private void initializeUI() {
        campName=getView().findViewById(R.id.campName);
        locationCamp =getView().findViewById(R.id.locationCamp);
        timeCamp=getView().findViewById(R.id.timeCamp);
        time1Camp=getView().findViewById(R.id.time1Camp);
        dateCamp=getView().findViewById(R.id.dateCamp);
        date1Camp=getView().findViewById(R.id.date1Camp);
    }
    private boolean validateCampName(){
        name=getView().findViewById(R.id.name);
        String Input = campName.getText().toString().trim();
        if (Input.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        }else if (!Pattern.compile("[ .a-zA-Z]+").matcher(Input).matches()) {
            name.setError("Please enter a valid name");
            return false;
        }
        else {
            name.setError(null);
            return true;
        }
    }
    private boolean validateCampLocation(){
        location=getView().findViewById(R.id.location);
        String Input = locationCamp.getText().toString().trim();
        if (Input.isEmpty()) {
            location.setError("Field can't be empty");
            return false;
        }
        else {
            location.setError(null);
            return true;
        }
    }
    private boolean validateCamptime0(){
        time0=getView().findViewById(R.id.time0);
        String Input = timeCamp.getText().toString().trim();
        if (Input.isEmpty()) {
            time0.setError("Field can't be empty");
            return false;
        }
        else {
            time0.setError(null);
            return true;
        }
    }
    private boolean validateCamptime1(){
        time1=getView().findViewById(R.id.time1);
        String Input = time1Camp.getText().toString().trim();
        if (Input.isEmpty()) {
            time1.setError("Field can't be empty");
            return false;
        }
        else {
            time1.setError(null);
            return true;
        }
    }
    private boolean validateCampdate(){
        date0=getView().findViewById(R.id.date0);
        String Input = dateCamp.getText().toString().trim();
        if (Input.isEmpty()) {
            date0.setError("Field can't be empty");
            return false;
        }
        else {
            date0.setError(null);
            return true;
        }
    }
    private boolean validateCampdate1(){
        date1=getView().findViewById(R.id.date1);
        String Input = date1Camp.getText().toString().trim();
        if (Input.isEmpty()) {
            date1.setError("Field can't be empty");
            return false;
        }
        else {
            date1.setError(null);
            return true;
        }
    }


}
