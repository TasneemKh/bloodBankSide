package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class userActivity extends AppCompatActivity {
    TextView name;
    ImageButton back;
    AppCompatTextView bloodType,mobile,weight,birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String i=getIntent().getStringExtra("name");
        String i1=getIntent().getStringExtra("wieght");
        String i2=getIntent().getStringExtra("birthday");
        String i3=getIntent().getStringExtra("phone");
        String i4=getIntent().getStringExtra("bloodTyp");
        name=findViewById(R.id.name);
        name.setText(i);
        weight=findViewById(R.id.weight);
        weight.setText(i1);
        birthday=findViewById(R.id.birthday);
        birthday.setText(i2);
        mobile=findViewById(R.id.mobile);
        mobile.setText(i3);
        bloodType=findViewById(R.id.bloodType);
        bloodType.setText(i4);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(userActivity.this,searchActivity.class);
                startActivity(i);
            }
        });






    }
}