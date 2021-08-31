package com.example.smarthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class Forgot_Password extends AppCompatActivity {
    private TextInputLayout mEmail;
    private Button mResetPassword;
//    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

  //      mAuth = FirebaseAuth.getInstance();

        mEmail = (TextInputLayout) findViewById(R.id.forgot_password_email);

        mResetPassword = (Button) findViewById(R.id.forgot_password_reset_btn);
   /*     mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddress = mEmail.getEditText().getText().toString();

                if(!TextUtils.isEmpty(emailAddress)){
                    mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(Forgot_Password.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Forgot_Password.this,"A mail has sent to Your Email",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Forgot_Password.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(Forgot_Password.this,"Please Enter Correct Email To Reset Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Forgot_Password.this,"Please Enter Email To Reset Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    */
    }


}
