package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
ImageButton back;
    TextInputEditText search;
   Button b;
   TextView no_result;
   RecyclerView recyclerView0;
    private LinearLayoutManager mLayoutManager;
    ArrayList<user> list0;
    String idNu;
    userAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(searchActivity.this,TabActivity.class);
                t.putExtra("reservation","1");
                startActivity(t);
            }
        });
        search=findViewById(R.id.search);
        no_result=findViewById(R.id.no_result);
        idNu=search.getText().toString();
        b=findViewById(R.id.button);
        recyclerView0 = findViewById(R.id.user_rv);
        recyclerView0.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView0.setLayoutManager(mLayoutManager);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").orderByChild("identityNumber")
                        .equalTo(idNu).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list0 = new ArrayList<user>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                user user = dataSnapshot1.getChildren().iterator().next().getValue(user.class);
                                list0.add(user);
                            }
                            userAdapter = new userAdapter(searchActivity.this, list0);
                            recyclerView0.setAdapter(userAdapter);
                        }else{

                            no_result.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });
            }
        });
       /* FirebaseDatabase.getInstance().getReference()
                .child("User").orderByChild("identityNumber").equalTo(idNu).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user user = dataSnapshot.getChildren().iterator().next().getValue(user.class);
                Toast.makeText(getApplicationContext(), ""+user.getFullname(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       */




    }
}