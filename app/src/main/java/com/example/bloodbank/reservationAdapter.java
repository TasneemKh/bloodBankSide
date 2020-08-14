package com.example.bloodbank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class reservationAdapter extends RecyclerView.Adapter<reservationAdapter.reservationVh>{
    Context context ;
    List<reservation> reservation ;
    DateFormat format;
    public  reservationAdapter(Context context,List<reservation> reservation ){
        this.context = context;
        this.reservation=reservation;
    }
    @NonNull
    @Override
    public reservationVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_row ,parent , false );
        return new reservationVh(view);    }

    @Override
    public void onBindViewHolder(@NonNull reservationVh holder, int position) {
        holder.setData(reservation.get(position));

    }

    @Override
    public int getItemCount() {
        return reservation.size();
    }



    class reservationVh extends RecyclerView.ViewHolder {
        TextView userName,type,time,hos;
        ImageView profileImg,imageButton2,imageButton3;
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth;
        String uid,name;
        FirebaseUser user;
        String uerUid;
        public reservationVh(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            hos = itemView.findViewById(R.id.hos);

            FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").orderByChild("Type").equalTo("hospital").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    String x=dataSnapshot1.child("uid").getValue(String.class);
                                    if((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(x)){
                                        String hosName=dataSnapshot1.child("Hospital_name").getValue(String.class);
                                        //   data.put("placeOfDonation", hosName);
                                        hos.setText(hosName);
                                        Toast.makeText(context,hosName , Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled (@NonNull DatabaseError error){

                        }
                    });
            profileImg=itemView.findViewById(R.id.profileImg);
            imageButton2=itemView.findViewById(R.id.imageButton2);
            imageButton3=itemView.findViewById(R.id.imageButton3);

        }

        public void setData(final reservation reservation) {
            type.setText(reservation.getType());
            time.setText(reservation.getTime());
            uerUid= reservation.getUid();
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            uid = user.getUid();
            FirebaseDatabase.getInstance().getReference().child("User").child(uerUid)
                    .addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            name=snapshot.child("userName").getValue(String.class);
                            userName.setText(name);

                            //Toast.makeText(context, " vv "+name, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,reservation.getUid()+" "+reservation.getName() , Toast.LENGTH_SHORT).show();
                }
            });
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("ReservationDon").child(uid)
                            .child(uerUid).removeValue();

                }
            });
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reservation.getType().equalsIgnoreCase("blood")){
                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(uerUid);
                        updateData.child("reminderPeriod").setValue(90);
                        updateData.child("drugDurations").setValue(0);
                    }else{
                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(uerUid);
                        updateData.child("reminderPeriod").setValue(14);
                        updateData.child("drugDurations").setValue(0);
                    }
                    String id= UUID.randomUUID().toString();
                    HashMap data = new HashMap<>();
                    data.put("donationType",reservation.getType());
                    String timeStamp=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                    String date=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                    data.put("dateOfDonation",timeStamp);
                    data.put("placeOfDonation", hos.getText().toString());

                   FirebaseDatabase.getInstance().getReference().child("donations").child(uerUid).child(id)
                            .setValue(data)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "on Failuer", Toast.LENGTH_SHORT).show();
                                    Log.d("error", e.getLocalizedMessage());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Your request is added", Toast.LENGTH_SHORT).show();

                                }
                            });
                    FirebaseDatabase.getInstance().getReference().child("ReservationDon").child(uid)
                            .child(uerUid).removeValue();

                }
            });

        }
    }
}
  /*imageButton3=itemView.findViewById(R.id.imageButton3);
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference("ReservationDon")
                            .child(uid).child(uerUid).removeValue();
                }
            });
/*package com.example.bloodbank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class reservationAdapter extends RecyclerView.Adapter<reservationAdapter.reservationVh>{
    Context context ;
    List<reservation> reservation ;
    DateFormat format;
    public  reservationAdapter(Context context,List<reservation> reservation ){
        this.context = context;
        this.reservation=reservation;
    }
    @NonNull
    @Override
    public reservationVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_row ,parent , false );
        return new reservationVh(view);    }

    @Override
    public void onBindViewHolder(@NonNull reservationVh holder, int position) {
        holder.setData(reservation.get(position));

    }

    @Override
    public int getItemCount() {
        return reservation.size();
    }



    class reservationVh extends RecyclerView.ViewHolder {
        TextView userName,type,time,hos;
        ImageView profileImg,imageButton2,imageButton3;
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        String name;
        String uerUid;
        String uid;
        FirebaseAuth mAuth ;
        FirebaseUser user;
        public reservationVh(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            hos = itemView.findViewById(R.id.hos);
           /* FirebaseDatabase.getInstance().getReference().child("Hospitals").child("Gaza").orderByChild("Type").equalTo("hospital").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    String x=dataSnapshot1.child("uid").getValue(String.class);
                                    if((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(x)){
                                        String hosName=dataSnapshot1.child("Hospital_name").getValue(String.class);
                                        //   data.put("placeOfDonation", hosName);
                                        hos.setText(hosName);
                                        Toast.makeText(context,hosName , Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled (@NonNull DatabaseError error){

                        }
                    });*/
           /* profileImg=itemView.findViewById(R.id.profileImg);
            imageButton2=itemView.findViewById(R.id.imageButton2);
            imageButton3=itemView.findViewById(R.id.imageButton3);

        }

        public void setData(final reservation reservation) {
           type.setText(reservation.getType());
            time.setText(reservation.getTime());
            uerUid= reservation.getUid();
             mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            uid = user.getUid();
            FirebaseDatabase.getInstance().getReference().child("User").child(uerUid)
                    .addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            name=snapshot.child("userName").getValue(String.class);
                            userName.setText(name);

                            //Toast.makeText(context, " vv "+name, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,reservation.getUid()+" "+reservation.getName() , Toast.LENGTH_SHORT).show();
                }
            });
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("ReservationDon").child(uid)
                            .child(uerUid).removeValue();

                }
            });
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (reservation.getType().equalsIgnoreCase("blood")){
                      DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(uerUid);
                      updateData.child("reminderPeriod").setValue(90);
                      updateData.child("drugDurations").setValue(0);
                  }else{
                      DatabaseReference updateData = FirebaseDatabase.getInstance().getReference().child("User").child(uerUid);
                      updateData.child("reminderPeriod").setValue(14);
                      updateData.child("drugDurations").setValue(0);
                  }
                    String id= UUID.randomUUID().toString();
                    HashMap data = new HashMap<>();
                    data.put("donationType",reservation.getType());
                    String timeStamp=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                    String date=new SimpleDateFormat("dd/MM/YYYY").format(Calendar.getInstance().getTime());
                    data.put("dateOfDonation",timeStamp);
                    data.put("placeOfDonation", hos.getText().toString());

                   /* FirebaseDatabase.getInstance().getReference().child("donations").child(uerUid).child(id)
                            .setValue(data)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "on Failuer", Toast.LENGTH_SHORT).show();
                                    Log.d("error", e.getLocalizedMessage());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Your request is added", Toast.LENGTH_SHORT).show();

                                }
                            });*/

                  /*  FirebaseDatabase.getInstance().getReference().child("ReservationDon").child(uid)
                            .child(uerUid).removeValue();

                }
            });

        }
    }
}*/
  /*imageButton3=itemView.findViewById(R.id.imageButton3);
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference("ReservationDon")
                            .child(uid).child(uerUid).removeValue();
                }
            });*/
           /* imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });*/