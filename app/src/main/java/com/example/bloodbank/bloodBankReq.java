package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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

import static android.view.View.GONE;

public class bloodBankReq extends AppCompatActivity {
ProgressBar pgsBar;
private FirebaseAuth mAuth;
FirebaseUser user;
RecyclerView recyclerView0;
DatabaseReference reference;
ImageButton back;
private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_req);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabActivity.class);
                startActivity(i);
                finish();
            }
        });
        pgsBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        recyclerView0 = findViewById(R.id.req_rv);
       // empty=getView().findViewById(R.id.empty);
        recyclerView0.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView0.setLayoutManager(mLayoutManager);
        reference = FirebaseDatabase.getInstance().getReference().child("bloodBankReq").child(uid);
        String date=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList list0 = new ArrayList<request>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        System.out.println(dataSnapshot1.getValue());
                        String UnitsType = dataSnapshot1.child("UnitsType").getValue(String.class);

                        String donType = dataSnapshot1.child("donType").getValue(String.class);
                        String date = dataSnapshot1.child("date").getValue(String.class);
                        String NoOfUnits = dataSnapshot1.child("NoOfUnits").getValue(String.class);
                        int NoOfUnits0=Integer.valueOf(NoOfUnits);
                        String idKey=dataSnapshot1.getKey();
                        long status = dataSnapshot1.child("status").getValue(long.class);
                       int status0=(int)status;
                        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        /*try {
                            date = format.parse(dateOfDonation);
                            System.out.println(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                        request p = new request(donType, UnitsType, NoOfUnits0,status0,date,idKey);

                        list0.add(p);
                    }
                    requestAdapter requestAdapter = new requestAdapter(bloodBankReq.this, list0);
                    int i = requestAdapter.getItemCount();
                    System.out.println(i);
                    pgsBar.setVisibility(GONE);
                    //empty.setVisibility(GONE);
                    recyclerView0.setAdapter(requestAdapter);
                }else{
                   // empty.setVisibility(View.VISIBLE);
                    pgsBar.setVisibility(GONE);

                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });
    }
}

/*package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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

import static android.view.View.GONE;

public class bloodBankReq extends AppCompatActivity {
ProgressBar pgsBar;
private FirebaseAuth mAuth;
FirebaseUser user;
RecyclerView recyclerView0;
DatabaseReference reference;
ImageButton back;
private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_req);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TabActivity.class);
                startActivity(i);
                finish();
            }
        });
        pgsBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        recyclerView0 = findViewById(R.id.req_rv);
       // empty=getView().findViewById(R.id.empty);
        recyclerView0.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView0.setLayoutManager(mLayoutManager);
        reference = FirebaseDatabase.getInstance().getReference().child("bloodBankReq").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList list0 = new ArrayList<request>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        System.out.println(dataSnapshot1.getValue());
                        String UnitsType = dataSnapshot1.child("UnitsType").getValue(String.class);

                        String donType = dataSnapshot1.child("donType").getValue(String.class);
                        String date = dataSnapshot1.child("date").getValue(String.class);
                        String NoOfUnits = dataSnapshot1.child("NoOfUnits").getValue(String.class);
                        int NoOfUnits0=Integer.valueOf(NoOfUnits);

                        long status = dataSnapshot1.child("status").getValue(long.class);
                       int status0=(int)status;
                        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        /*try {
                            date = format.parse(dateOfDonation);
                            System.out.println(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
/*request p = new request(donType, UnitsType, NoOfUnits0,status0,date);

                        list0.add(p);
                                }
                                requestAdapter requestAdapter = new requestAdapter(bloodBankReq.this, list0);
                                int i = requestAdapter.getItemCount();
                                System.out.println(i);
                                pgsBar.setVisibility(GONE);
                                //empty.setVisibility(GONE);
                                recyclerView0.setAdapter(requestAdapter);
                                }else{
                                // empty.setVisibility(View.VISIBLE);
                                pgsBar.setVisibility(GONE);

                                }
                                }

@Override
public void onCancelled (@NonNull DatabaseError error){

        }
        });
        }
        }*/