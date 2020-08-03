package com.example.bloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        TextView userName,type,time;
        ImageView profileImg,imageButton2,imageButton3;

        public reservationVh(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            profileImg=itemView.findViewById(R.id.profileImg);
            imageButton2=itemView.findViewById(R.id.imageButton2);
            imageButton3=itemView.findViewById(R.id.imageButton3);
        }

        public void setData(final reservation reservation) {
           type.setText(reservation.getType());

            //profileImg.setImageDrawable(R.drawable.);
           // time.setText(String.reservation.getTime());

           /*  bloodbank.setText(donationHistory.getPlaceOfDonation());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormat.format(donationHistory.getDateOfDonation()));*/
            //date.setText(format.format(new Date(String.valueOf(donationHistory.getDateOfDonation()))));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, reservation.getUid(), Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(itemView.getContext() ,stdActivity.class);
                  //  intent.putExtra("id",donationHistory.getId());
                    intent.putExtra("name",donationHistory.getName());
                    intent.putExtra("level",donationHistory.getLevel());
                    intent.putExtra("Avg",donationHistory.getAvg());
                    itemView.getContext().startActivity(intent);*/
                }
            });

        }
    }
}