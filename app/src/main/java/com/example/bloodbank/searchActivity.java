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
    SearchView search;
   Button b;
   TextView no_result;
   RecyclerView recyclerView0;
    private LinearLayoutManager mLayoutManager;
    ArrayList<user> list0;
     String userSearch;
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
        recyclerView0 = findViewById(R.id.user_rv);
        recyclerView0.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView0.setLayoutManager(mLayoutManager);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 userSearch = search.getQuery().toString();
                if (!TextUtils.isEmpty(userSearch)) {
                    FirebaseDatabase.getInstance().getReference().child("User").
                            orderByChild("identityNumber").equalTo(userSearch)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    list0 = new ArrayList<user>();

                                    if (dataSnapshot.exists()) {
                                        no_result.setVisibility(View.INVISIBLE);

                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                            String x=dataSnapshot1.child("identityNumber").getValue(String.class);

                                                if(userSearch.equals(x)){
                                                  String  x2=dataSnapshot1.child("userName").getValue(String.class);
                                                    String  x3=dataSnapshot1.child("birthday").getValue(String.class);
                                                    String  x4=dataSnapshot1.child("bloodType").getValue(String.class);
                                                    String  x5=dataSnapshot1.child("weight").getValue(String.class);
                                                    String  x6=dataSnapshot1.child("phoneNumber").getValue(String.class);
                                                    int  x7=dataSnapshot1.child("reminderPeriod").getValue(Integer.class);
                                                    int x8=dataSnapshot1.child("drugDurations").getValue(Integer.class);
                                                    String uid=dataSnapshot1.getKey();
                                                   // Toast.makeText(getApplicationContext(), " vv "+x +uid+x2, Toast.LENGTH_SHORT).show();
                                                    user user0=new user(x6,x5, x4 , x2,x3, uid,x8,x7);
                                                    list0.add(user0);
                                                }

                                        }
                                        userAdapter = new userAdapter(searchActivity.this, list0);
                                        recyclerView0.setAdapter(userAdapter);
                                    }else{

                                        no_result.setVisibility(View.VISIBLE);

                                    }
                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError error){

                                }
                            });


                } else {
                    Toast.makeText(getApplicationContext(), "Please, write your search", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }
}

 /*
               */
// Toast.makeText(getApplicationContext(), " vv "+x +uid, Toast.LENGTH_SHORT).show();
// String x1=dataSnapshot.child("userName").getValue(String.class);

// Toast.makeText(getApplicationContext(), " vv "+dataSnapshot.child("userName").getValue(String.class), Toast.LENGTH_SHORT).show();
//  String user = dataSnapshot1.getChildren().iterator().next().getValue(String.class);
// String user2 = dataSnapshot1.getChildren().iterator().next().getValue(String.class);

///   user useddr = dataSnapshot1.getValue(user.class);
//  Toast.makeText(getApplicationContext(), "try " + useddr.getFullname(), Toast.LENGTH_SHORT).show();


//         String user7 = dataSnapshot1.getChildren().iterator().next().next().getValue(String.class);

//Toast.makeText(getApplicationContext(), " "+user+user2, Toast.LENGTH_SHORT).show();
 /*  b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").orderByChild("identityNumber").equalTo(idNu)
                       .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list0 = new ArrayList<user>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Toast.makeText(getApplicationContext(), " vv "+dataSnapshot.child("userName").getValue(String.class), Toast.LENGTH_SHORT).show();
                                user user = dataSnapshot1.getChildren().iterator().next().getValue(user.class);
                                Toast.makeText(getApplicationContext(), " "+user.getFullname(), Toast.LENGTH_SHORT).show();

                                list0.add(user);
                            }
                            userAdapter = new userAdapter(searchActivity.this, list0);
                            recyclerView0.setAdapter(userAdapter);
                        }else{

                            no_result.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });
            }
        });*/
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
         /*  Toast.makeText(getApplicationContext(), " vv "+x +uid, Toast.LENGTH_SHORT).show();
                                            // String x1=dataSnapshot.child("userName").getValue(String.class);

                                            // Toast.makeText(getApplicationContext(), " vv "+dataSnapshot.child("userName").getValue(String.class), Toast.LENGTH_SHORT).show();
                                            String user = dataSnapshot1.getChildren().iterator().next().getValue(String.class);
                                            String user2 = dataSnapshot1.getChildren().iterator().next().getValue(String.class);

                                            user useddr = dataSnapshot1.getValue(user.class);
                                            Toast.makeText(getApplicationContext(), "try " + useddr.getFullname(), Toast.LENGTH_SHORT).show();
*/

//         String user7 = dataSnapshot1.getChildren().iterator().next().next().getValue(String.class);

//  Toast.makeText(getApplicationContext(), " "+user+user2, Toast.LENGTH_SHORT).show();

/*  FirebaseDatabase.getInstance().getReference().child("User").orderByChild("identityNumber").equalTo(idNu)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    list0 = new ArrayList<user>();
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                            String x=dataSnapshot.child("identityNumber").getValue(String.class);
                                            Toast.makeText(getApplicationContext(), "1 " +x, Toast.LENGTH_SHORT).show();

                                            if (userSearch.equals(x)){

                                                String x1= dataSnapshot.child("userName").getValue(String.class);
                                                Toast.makeText(getApplicationContext(), "1 " +x1, Toast.LENGTH_SHORT).show();

                                                String x2= dataSnapshot.getKey();
                                                String x3= dataSnapshot.child("weight").getValue(String.class);
                                                String x4= dataSnapshot.child("birthday").getValue(String.class);
                                                String x5= dataSnapshot.child("bloodType").getValue(String.class);
                                                String x6= dataSnapshot.child("phoneNumber").getValue(String.class);

                                                user user0=new user(x6,x3,x5,x1,x4, x2) ;
                                                list0.add(user0);
                                            }

                                        }
                                        userAdapter = new userAdapter(searchActivity.this, list0);
                                        recyclerView0.setAdapter(userAdapter);
                                    }else{

                                        no_result.setVisibility(View.VISIBLE);

                                    }
                                }

                                @Override
                                public void onCancelled (@NonNull DatabaseError error){

                                }
                            });*/