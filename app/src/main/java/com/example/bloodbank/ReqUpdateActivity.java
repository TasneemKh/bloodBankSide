package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReqUpdateActivity extends AppCompatActivity {
    TextInputLayout typeOfDonation,bloodType,numUnits;
    EditText donType,UnitsType,NoOfUnits;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    SwitchMaterial simpleSwitch;
    String[] arrayPickerType= new String[]{"O+","O-","A+","A-","B+","B-","AB+","AB-",};
    int numOfProducts;
    // String[] arrayPickerType= new String[]{"o positive","o negative","A positive","A negative","B positive","B negative","AB positive","AB negative"};
    String[] arrayPickerDonType= new String[]{"Blood","Platelets"};
    String input4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_update);
        String input0 = getIntent().getStringExtra("status");
        String input10 = getIntent().getStringExtra("NoOfUnits");
        String input20 = getIntent().getStringExtra("donType");
        String input30 = getIntent().getStringExtra("UnitsType");
         input4 = getIntent().getStringExtra("idKey");
        donType=findViewById(R.id.donType); donType.setText(input20);
        UnitsType=findViewById(R.id.UnitsType); UnitsType.setText(input30);
        NoOfUnits=findViewById(R.id.NoOfUnits); NoOfUnits.setText(input10);
        simpleSwitch = findViewById(R.id.switch1);
        int i=Integer.parseInt(input0);
        if(i==0){simpleSwitch.setChecked(false);}
        else{simpleSwitch.setChecked(true);}



        NoOfUnits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show1();
            }
        });
        UnitsType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show2();
            }
        });
        donType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show3();
            }
        });
       findViewById(R.id.Update).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!validatetypeOfDonation() | !validatebloodType() | !validatenumUnits() ) {
                    return;
                }
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                String uid = user.getUid();
                //String id= UUID.randomUUID().toString();

                String Input = donType.getText().toString().trim();
                String Input1 = UnitsType.getText().toString().trim();
                String Input2 = NoOfUnits.getText().toString().trim();
                int Input3 ;
                Boolean switchState = simpleSwitch.isChecked();
                if (switchState){
                    //true
                    Input3=1;
                }else{
                    //false
                    Input3=0;
                }

                // data.put("date",dtf.format(now));
                DatabaseReference updateData= FirebaseDatabase.getInstance().getReference().child("bloodBankReq").
                        child(uid).child(input4);
                updateData.child("NoOfUnits").setValue(Input2);
                updateData.child("UnitsType").setValue(Input1);
                updateData.child("donType").setValue(Input);
                updateData.child("status").setValue(Input3);
                Toast.makeText(getApplicationContext(), "Your request is updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ReqUpdateActivity.this,bloodBankReq.class));
                       /* .addOnFailureListener(new OnFailureListener() {
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
                                donType.setText("");
                                UnitsType.setText("");
                                NoOfUnits.setText("");

                            }
                        });*/

            }
       });
    }
    private boolean validatetypeOfDonation(){
        typeOfDonation=findViewById(R.id.typeOfDonation);
        String Input = donType.getText().toString().trim();
        if (Input.isEmpty()) {
            typeOfDonation.setError("Field can't be empty");
            return false;
        }else {
            typeOfDonation.setError(null);
            return true;
        }
    }
    private boolean validatebloodType(){
        bloodType=findViewById(R.id.bloodType);
        String Input = UnitsType.getText().toString().trim();
        if (Input.isEmpty()) {
            bloodType.setError("Field can't be empty");
            return false;
        }else {
            bloodType.setError(null);
            return true;
        }
    }
    private boolean validatenumUnits(){
        numUnits=findViewById(R.id.numUnits);
        String Input = NoOfUnits.getText().toString().trim();
        if (Input.isEmpty()) {
            numUnits.setError("Field can't be empty");
            return false;
        }else {
            numUnits.setError(null);
            return true;
        }
    }
    public void show1(){
        final AlertDialog.Builder d = new AlertDialog.Builder(ReqUpdateActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker, null);
        d.setTitle("Set Number Of Units Needed");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.unitsNumber);
        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoOfUnits.setText(String.valueOf(numberPicker.getValue()));
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
    public void show2() {

        final AlertDialog.Builder d = new AlertDialog.Builder(ReqUpdateActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker, null);
        d.setTitle("Set blood Type Needed");
        d.setView(dialogView);
        final NumberPicker pickers = (NumberPicker) dialogView.findViewById(R.id.unitsNumber);
        pickers.setMinValue(0);
        pickers.setMaxValue(arrayPickerType.length - 1);
        pickers.setDisplayedValues(arrayPickerType);
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
                String selecPicker = arrayPickerType[u];
                UnitsType.setText(selecPicker);
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

        final AlertDialog.Builder d = new AlertDialog.Builder(ReqUpdateActivity.this);
        LayoutInflater inflater =getLayoutInflater();
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
                String selecPicker = arrayPickerDonType[u];
                donType.setText(selecPicker);
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