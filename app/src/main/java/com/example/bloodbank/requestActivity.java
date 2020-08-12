package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class requestActivity  extends Fragment {
    //TextInputLayout typeOfDonation,bloodType,numUnits;
    AppCompatTextView donType,UnitsType,NoOfUnits;
   // EditText donType,UnitsType,NoOfUnits;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    SwitchMaterial simpleSwitch;
    String[] arrayPickerType= new String[]{"O+","O-","A+","A-","B+","B-","AB+","AB-",};
    int numOfProducts;
    ImageButton viewReq;
   // String[] arrayPickerType= new String[]{"o positive","o negative","A positive","A negative","B positive","B negative","AB positive","AB negative"};
    String[] arrayPickerDonType= new String[]{"Blood","Platelets"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_request, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        donType=getView().findViewById(R.id.donType);
        UnitsType=getView().findViewById(R.id.UnitsType);
        NoOfUnits=getView().findViewById(R.id.NoOfUnits);
        simpleSwitch = getView().findViewById(R.id.switch1);
        viewReq= getView().findViewById(R.id.viewReq);
        viewReq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), bloodBankReq.class);
                startActivity(i);
            }
        });
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
        getView().findViewById(R.id.submit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!validatetypeOfDonation() | !validatebloodType() | !validatenumUnits() ) {
                    return;
                }
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                String uid = user.getUid();
                String id= UUID.randomUUID().toString();
                Map<String,Object> data = new HashMap<>();
                DateTimeFormatter dtf = null;
                LocalDateTime now;
                String Input4;
                String Input = donType.getText().toString().trim();
                String Input1 = UnitsType.getText().toString().trim();
                String Input2 = NoOfUnits.getText().toString().trim();
                int Input3 ;
                data.put("donType",Input);
                data.put("UnitsType",Input1);
                data.put("NoOfUnits",Input2);
                String timeStamp=new SimpleDateFormat("dd/MM/YYYY HH:mm").format(Calendar.getInstance().getTime());
                String date=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                data.put("createdAt",timeStamp);
                Boolean switchState = simpleSwitch.isChecked();
                if (switchState){
                    //true
                    Input3=1;
                }else{
                   //false
                    Input3=0;
                }
                data.put("status",Input3);
               data.put("date",date);
                FirebaseDatabase.getInstance().getReference().child("bloodBankReq").child(uid).child(id).setValue(data)
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
                                donType.setText("");
                                UnitsType.setText("");
                                NoOfUnits.setText("");

                            }
                        });
            }
        });


    }
    private boolean validatetypeOfDonation(){
       // typeOfDonation=getView().findViewById(R.id.typeOfDonation);
        String Input = donType.getText().toString().trim();
        if (Input.isEmpty()) {
           // typeOfDonation.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else {
           // typeOfDonation.setError(null);
            return true;
        }
    }
    private boolean validatebloodType(){
        //bloodType=getView().findViewById(R.id.bloodType);
        String Input = UnitsType.getText().toString().trim();
        if (Input.isEmpty()) {
            //bloodType.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }else {
            //bloodType.setError(null);
            return true;
        }
    }
    private boolean validatenumUnits(){
      //  numUnits=getView().findViewById(R.id.numUnits);
        String Input = NoOfUnits.getText().toString().trim();
        if (Input.isEmpty()) {
           // numUnits.setError("Field can't be empty");
            Toast.makeText(getActivity().getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }else {
          //  numUnits.setError(null);
            return true;
        }
    }
    public void show1(){
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
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

        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
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

        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
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

  /* FirebaseDatabase.getInstance().getReference().child("bloodBankReq").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        numOfProducts = (int) dataSnapshot.getChildrenCount();
                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });*/
//++numOfProducts;
/* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    now= LocalDateTime.now();
                    Input4=dtf.format(now);
                }
*/