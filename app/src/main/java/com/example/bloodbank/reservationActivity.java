package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                                DatabaseReference data=FirebaseDatabase.getInstance().getReference().
                                        child("User").child(userId).child("userName");
                                data.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        name=dataSnapshot.getValue(String.class);
                                        Toast.makeText(getActivity().getApplicationContext(), ""+name, Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                String c=name;
                                //String name=data.child("userName").getValue(String.class);

                              /*  reservation p = new reservation(uid,date,time,name,donType);

                                list0.add(p);*/
                              /*  final reservation p = new reservation();
                                String date = dataSnapshot1.child("date").getValue(String.class);p.setDate(date);
                                String donType = dataSnapshot1.child("donType").getValue(String.class);p.setType(donType);
                                String time = dataSnapshot1.child("time").getValue(String.class);p.setTime(time);
                                String userId = dataSnapshot1.child("userId").getValue(String.class);p.setUid(userId);
                                DatabaseReference data=FirebaseDatabase.getInstance().getReference().
                                        child("User").child(userId);
                                data.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        name=dataSnapshot.child("userName").getValue(String.class);
                                        p.setName(name);
                                        Toast.makeText(getActivity().getApplicationContext(), ""+name, Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });*/
                                //String name=data.child("userName").getValue(String.class);

                               reservation p = new reservation(uid,date,time,c,donType);

                                list0.add(p);
                            }
                            reservationAdapter reservationAdapter = new reservationAdapter(getActivity(), list0);
                            int i = reservationAdapter.getItemCount();
                           // System.out.println(i);
                            pgsBar.setVisibility(GONE);
                            //.setVisibility(GONE);
                            recyclerView0.setAdapter(reservationAdapter);
                        }else{
                            //empty.setVisibility(View.VISIBLE);
                            pgsBar.setVisibility(GONE);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

    }
}