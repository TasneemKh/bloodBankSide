package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.view.View.GONE;

public class userActivity extends AppCompatActivity {
    TextView name,hos;
    ImageButton back;
    Button add,edit;
    AppCompatTextView bloodType,mobile,weight,birthday;
    String[] arrayPickerDonType= new String[]{"Blood","Platelets"};
    String i5;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String uid,b;
    Map<String,Object> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        String i=getIntent().getStringExtra("name");
        String i1=getIntent().getStringExtra("wieght");
        String i2=getIntent().getStringExtra("birthday");
        String i3=getIntent().getStringExtra("phone");
        String i4=getIntent().getStringExtra("bloodTyp");
         i5=getIntent().getStringExtra("uid");
        int drugDurations=getIntent().getIntExtra("drugDurations",0);
        int reminderPeriod=getIntent().getIntExtra("reminderPeriod",0);
        hos=findViewById(R.id.hos);
        FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").orderByChild("Type").equalTo("hospital").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String x=dataSnapshot1.child("uid").getValue(String.class);
                                if(uid.equals(x)){
                                    String hosName=dataSnapshot1.child("Hospital_name").getValue(String.class);
                                    //   data.put("placeOfDonation", hosName);
                                    hos.setText(hosName);
                                    Toast.makeText(getApplicationContext(),hosName , Toast.LENGTH_SHORT).show();
                                    b=hosName;
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });
        name=findViewById(R.id.name);
        name.setText(i);
        weight=findViewById(R.id.weight);
        weight.setText(i1);
        birthday=findViewById(R.id.birthday);
        birthday.setText(i2);
        mobile=findViewById(R.id.mobile);
        mobile.setText(i3);
        bloodType=findViewById(R.id.bloodType);
        bloodType.setText(i4);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(userActivity.this,searchActivity.class);
                startActivity(i);
            }
        });
        add=findViewById(R.id.add);

        if(drugDurations==0 && reminderPeriod==0){
            add.setEnabled(true);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show3();
                }
            });
        }else{
            add.setEnabled(false);
            Toast.makeText(getApplicationContext(),"User can not donate right now",Toast.LENGTH_SHORT).show();
        }

        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show1();
            }
        });


    }
    public void show1(){
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker, null);
        d.setTitle("Set A new Duration");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.unitsNumber);
        numberPicker.setMaxValue(120);
        numberPicker.setMinValue(60);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // NoOfUnits.setText(String.valueOf(numberPicker.getValue()));
                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(i5);
                updateData.child("reminderPeriod").setValue(numberPicker.getValue());
                updateData.child("drugDurations").setValue(0);
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
    public void show3() {

        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker, null);
        d.setTitle("Set donation Type");
        d.setView(dialogView);
        final NumberPicker pickers = (NumberPicker) dialogView.findViewById(R.id.unitsNumber);
        pickers.setMinValue(0);
        pickers.setMaxValue(arrayPickerDonType.length - 1);
        pickers.setDisplayedValues(arrayPickerDonType);
        pickers.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickers.setWrapSelectorWheel(true);
        pickers.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int u =pickers.getValue();
                String id= UUID.randomUUID().toString();
                String selecPicker= arrayPickerDonType[u];
                data = new HashMap<>();
                data.put("donationType",selecPicker);


                String timeStamp=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                String date=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                data.put("dateOfDonation",timeStamp);
                data.put("placeOfDonation", hos.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("donations").child(i5).child(id)
                        .setValue(data)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "on Failuer", Toast.LENGTH_SHORT).show();
                                Log.d("error", e.getLocalizedMessage());
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Your request is added", Toast.LENGTH_SHORT).show();

                            }
                        });
                if(selecPicker.equals("Blood")){
                    DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(i5);
                    updateData.child("reminderPeriod").setValue(90);
                    updateData.child("drugDurations").setValue(0);
                }else{
                    DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(i5);
                    updateData.child("reminderPeriod").setValue(14);
                    updateData.child("drugDurations").setValue(0);
                }
                //donType.setText(selecPicker);
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