package com.example.bloodbank;

        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.PopupMenu;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.FirebaseDatabase;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;

public class requestAdapter extends RecyclerView.Adapter<requestAdapter.requestVh>{
    Context context ;
    List<request> request ;
    DateFormat format;

    public  requestAdapter(Context context,List<request> request ){
        this.context = context;
        this.request=request;
    }
    @NonNull
    @Override
    public requestVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blood_bank_req_row ,parent , false );
        return new requestVh(view);    }

    @Override
    public void onBindViewHolder(@NonNull requestVh holder, int position) {
        holder.setData(request.get(position));

    }

    @Override
    public int getItemCount() {
        return request.size();
    }



    class requestVh extends RecyclerView.ViewHolder {
        TextView noOfUnits,date,status,bloodTyp;
        ImageButton menu;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth;
        FirebaseUser user;
        String idKey;
        public requestVh(@NonNull View itemView) {
            super(itemView);
            noOfUnits = itemView.findViewById(R.id.noOfUnits);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            bloodTyp=itemView.findViewById(R.id.bloodTyp);
            menu=itemView.findViewById(R.id.imageButton4);
          /*  imageButton2=itemView.findViewById(R.id.imageButton2);
            imageButton3=itemView.findViewById(R.id.imageButton3);*/
        }

        public void setData(final request request) {
            String v=request.getNoOfUnits()+" "+request.getDonType();
            noOfUnits.setText(v);
          //  date
            int i=request.getStatus();
            String z;
            if(i==0){z="NOT URGENT";}
            else{z="URGENT";}
            status.setText(z);
            bloodTyp.setText(request.getUnitsType());
            date.setText(request.getDate());
             idKey=request.getIdKey();
            //profileImg.setImageDrawable(R.drawable.);
            // time.setText(String.reservation.getTime());

           /*  bloodbank.setText(donationHistory.getPlaceOfDonation());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormat.format(donationHistory.getDateOfDonation()));*/
            //date.setText(format.format(new Date(String.valueOf(donationHistory.getDateOfDonation()))));
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(context, menu);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.drop_menu_del_edit, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            mAuth = FirebaseAuth.getInstance();
                            user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            switch (item.getItemId()) {
                                case R.id.edit:
                                    Intent intent = new Intent(context, ReqUpdateActivity.class);
                                    intent.putExtra("status", Integer.toString(request.getStatus()));
                                    intent.putExtra("NoOfUnits", Integer.toString(request.getNoOfUnits()));
                                    intent.putExtra("donType", request.getDonType());
                                    intent.putExtra("UnitsType", request.getUnitsType());
                                    intent.putExtra("idKey", request.getIdKey());
                                    context.startActivity(intent);
                                    return true;
                                case R.id.delete:

                                    database.getReference("bloodBankReq")
                                            .child(uid).child(idKey).removeValue();
                                    return true;

                            }
                            return false;
                        }

                    });
                    popup.show(); //showing popup menu
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, request.getUnitsType(), Toast.LENGTH_SHORT).show();
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
/*  public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(
                                    context,
                                    "You Clicked : " + item.getTitle(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            //item.getItemId()
                            return true;
                        }
                        v
                            return false;

                        }*/