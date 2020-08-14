package com.example.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userVh>{
    Context context ;
    List<user> user ;
    DateFormat format;
    public  userAdapter(Context context,List<user> user ){
        this.context = context;
        this.user=user;
    }
    @NonNull
    @Override
    public userVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searh_result_row ,parent , false );
        return new userVh(view);    }

    @Override
    public void onBindViewHolder(@NonNull userVh holder, int position) {
        holder.setData(user.get(position));

    }

    @Override
    public int getItemCount() {
        return user.size();
    }



    class userVh extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public userVh(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }

        public void setData(final user user) {
            Toast.makeText(context, "in"+user.getFullname() , Toast.LENGTH_SHORT).show();
           name.setText(user.getFullname());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(context, user.getHospital_name(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext() ,userActivity.class);
                    intent.putExtra("name",user.getFullname());
                    intent.putExtra("wieght",user.getWeight());
                    intent.putExtra("birthday",user.getBirthday());
                    intent.putExtra("phone",user.getPhoneNumber());
                    intent.putExtra("bloodTyp",user.getBloodType());
                    intent.putExtra("uid",user.getUid());
                    intent.putExtra("drugDurations",user.getDrugDurations());
                    intent.putExtra("reminderPeriod",user.getReminderPeriod());

                    itemView.getContext().startActivity(intent);


                }
            });

        }
    }
}
 /* location.setText(Campaigns.getLocation());
            String x=Campaigns.getNo_units_needed() +" BLOOD UNIT NEEDED";
            numberOfUnits.setText(x);
            String y=Campaigns.getType();
            if (y.equals("campaign")){
                image.setImageResource(R.drawable.campaigns);

            }else{
                image.setImageResource(R.drawable.hospitals);
            }*/