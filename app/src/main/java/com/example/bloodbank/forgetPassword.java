package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {
    ImageButton back;
    Button recoverPassword;
    TextView email_rec;
    float x1,y1,x2,y2;
    TextInputLayout til;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //getting back to log in activity
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), logInActivity.class);
                startActivity(i);
            }
        });
        //recoverPassword.getOnFocusChangeListener(til.setError("Please ,fill the space"));
        //recover pass using email
        til = (TextInputLayout) findViewById(R.id.e_mail);
        recoverPassword=findViewById(R.id.recoverPassword);
        recoverPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                email_rec=findViewById(R.id.e_mail1);
                String emailAddress = email_rec.getText().toString();
                if(!(emailAddress.isEmpty())) {
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Log.d(TAG, "Email sent.");
                                        Toast.makeText(getApplicationContext(), "Please ,review your email", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), logInActivity.class);
                                        startActivity(i);
                                    } else {
                                        //  Toast.makeText(getApplicationContext(), "Please ,enter a valid email", Toast.LENGTH_LONG).show();
                                        //  Log.d("MEDIA_PLAYER", new Exception().getMessage());
                                        til.setError("Enter a valid email");
                                        // Toast.makeText(getApplicationContext(), "Please ,enter a valid email", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    til.setError("Please ,fill the space");
                }
            }
        });
    }


}