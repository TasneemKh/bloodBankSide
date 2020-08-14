package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class reservationActivity  extends Fragment {
    FirebaseAuth mAuth;
    String uid;
    FirebaseUser user;
    ArrayList<reservation> list0;
    ProgressBar pgsBar;
    RecyclerView recyclerView0;
    private String name;
    private LinearLayoutManager mLayoutManager;
ImageButton search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_reservation, container, false);
    }
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        pgsBar = (ProgressBar)getView().findViewById(R.id.pBar);
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        search=getActivity().findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity().getApplicationContext(),searchActivity.class);
                startActivity(i);
            }
        });
        recyclerView0 = getView().findViewById(R.id.reservation_rv);
        recyclerView0.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView0.setLayoutManager(mLayoutManager);
        FirebaseDatabase.getInstance().getReference().child("ReservationDon").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        list0 = new ArrayList<reservation>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                String date = dataSnapshot1.child("date").getValue(String.class);
                                String donType = dataSnapshot1.child("donType").getValue(String.class);
                                String time = dataSnapshot1.child("time").getValue(String.class);
                                String userId = dataSnapshot1.child("userId").getValue(String.class);


                               reservation p = new reservation(userId,date,time,name,donType);

                                list0.add(p);
                            }
                            reservationAdapter reservationAdapter = new reservationAdapter(getActivity(), list0);
                            int i = reservationAdapter.getItemCount();
                            pgsBar.setVisibility(GONE);
                            recyclerView0.setAdapter(reservationAdapter);
                        }else{
                            pgsBar.setVisibility(GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

    }
}
