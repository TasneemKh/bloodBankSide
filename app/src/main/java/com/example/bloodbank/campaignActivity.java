package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class campaignActivity extends Fragment {
   // EditText campName,locationCamp,timeCamp,time1Camp,dateCamp,date1Camp;
   // TextInputLayout name,location,time0,time1,date0,date1;
    AppCompatTextView timeCamp,time1Camp,dateCamp,date1Camp;
    AppCompatEditText campName,locationCamp;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    int numOfProducts,hour,min;
    DatePickerDialog picker;
    Calendar c;
String S,S1;

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
        timeCamp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time1();
            }
        });
        time1Camp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time2();
            }
        });
        dateCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                //Date currentTime = cldr.getInstance().getTime();

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cldr.set(year,monthOfYear,dayOfMonth);
                                c=cldr;
                                SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
                                String datetime = dateformat.format(cldr.getTime());
                                dateCamp.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String weekDay;
                                SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);
                                Calendar calendar = cldr.getInstance();
                                weekDay = dayFormat.format(calendar.getTime());
                                S1=weekDay;
                               /* DateTimeFormatter dayOfWeekFormatter
                                        = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
                                    LocalDate date = LocalDate.of(year,monthOfYear,dayOfMonth);
                                    S1=date.format(dayOfWeekFormatter);
                                }*/
                            }

                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                picker.show();
            }
        });
        date1Camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr2 = Calendar.getInstance();
                int day = cldr2.get(Calendar.DAY_OF_MONTH);
                int month = cldr2.get(Calendar.MONTH);
                int year = cldr2.get(Calendar.YEAR);

                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date1Camp.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String weekDay;
                                SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);
                                Calendar calendar = cldr2.getInstance();
                                weekDay = dayFormat.format(calendar.getTime());
                                S=weekDay;
                             /*   DateTimeFormatter dayOfWeekFormatter
                                        = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
                                    LocalDate date = LocalDate.of(year,monthOfYear,dayOfMonth);
                                    S=date.format(dayOfWeekFormatter);
                                }*/

                            }

                        }, year, month, day);
                picker.getDatePicker().setMinDate(c.getTimeInMillis());

                picker.show();
            }
        });
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
                String timeStamp=new SimpleDateFormat("dd/MM/YY HH:mm").format(Calendar.getInstance().getTime());

                data.put("createdAt",timeStamp);
                String x=S+" - "+S1;
                data.put("workDays",x);
                //data.put("workHours",);
                /*FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        numOfProducts = (int) dataSnapshot.getChildrenCount();
                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });*/
               // ++numOfProducts;
                data.put("id",id);
                FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").child(id).setValue(data)
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
                                campName.setText("");
                                locationCamp.setText("");
                                timeCamp.setText("");
                                time1Camp.setText("");
                                dateCamp.setText("");
                                date1Camp.setText("");

                            }
                        });
            }
        });


    }
    private void initializeUI() {
        campName=(AppCompatEditText)getView().findViewById(R.id.campName);
        locationCamp =(AppCompatEditText)getView().findViewById(R.id.locationCamp);
        timeCamp=(AppCompatTextView)getView().findViewById(R.id.timeCamp);
        time1Camp=(AppCompatTextView)getView().findViewById(R.id.time1Camp);
        dateCamp=(AppCompatTextView)getView().findViewById(R.id.dateCamp);
        date1Camp=(AppCompatTextView)getView().findViewById(R.id.date1Camp);
    }
    private boolean validateCampName(){
       // name=getView().findViewById(R.id.name);
        String Input = campName.getText().toString().trim();
        if (Input.isEmpty()) {
           // name.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }else if (!Pattern.compile("[ .a-zA-Z]+").matcher(Input).matches()) {
           // name.setError("Please enter a valid name");
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
            //name.setError(null);
            return true;
        }
    }
    private boolean validateCampLocation(){
       // location=getView().findViewById(R.id.location);
        String Input = locationCamp.getText().toString().trim();
        if (Input.isEmpty()) {
           // location.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
         //   location.setError(null);
            return true;
        }
    }
    private boolean validateCamptime0(){
    //    time0=getView().findViewById(R.id.time0);
        String Input = timeCamp.getText().toString().trim();
        if (Input.isEmpty()) {
          //  time0.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
          //  time0.setError(null);
            return true;
        }
    }
    private boolean validateCamptime1(){
       // time1=getView().findViewById(R.id.time1);
        String Input = time1Camp.getText().toString().trim();
        if (Input.isEmpty()) {
          //  time1.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
            //time1.setError(null);
            return true;
        }
    }
    private boolean validateCampdate(){
     //   date0=getView().findViewById(R.id.date0);
        String Input = dateCamp.getText().toString().trim();
        if (Input.isEmpty()) {
           // date0.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
           // date0.setError(null);
            return true;
        }
    }
    private boolean validateCampdate1(){
       // date1=getView().findViewById(R.id.date1);
        String Input = date1Camp.getText().toString().trim();
        if (Input.isEmpty()) {
         //   date1.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else {
            //date1.setError(null);
            return true;
        }
    }
    public void time1(){
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.time_picker, null);
        d.setTitle("Set The Start Time Of Campaigns");
        d.setView(dialogView);
        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(8); // before api level 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(8);
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int x=timePicker.getHour();
                    int x1=timePicker.getMinute();
                }

                timeCamp.setText( +hour +" : " + min);
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();

    }
    public void time2(){
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.time_picker, null);
        d.setTitle("Set The End Time Of Campaigns");
        d.setView(dialogView);
        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(8); // before api level 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(8);
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int x=timePicker.getHour();
                    int x1=timePicker.getMinute();
                }

                time1Camp.setText( +hour +" : " + min);
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();

    }

}
