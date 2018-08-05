package com.bmc304.wincci.bmc304gamificationapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

        protected EditText passwordEditText;
        protected EditText emailEditText;
        protected Button signUpButton;
        private FirebaseAuth mFirebaseAuth;
        protected TextView loginTextView;
        private RadioButton sign_up_rbAdmin, sign_up_rbStudent;
        private String email, uid;
        private FirebaseUser firebaseUser;
        private FirebaseDatabase database;
        private DatabaseReference ref;
        private User user;
        private double score;
        private String userType;
        private String status;
        private String username;
        private String image;
        private double longitude = 123.1,latitude = 123.1;
        private static final String TAG = "MainActivity";
        private Location location;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            // Initialize FirebaseAuth


            mFirebaseAuth = FirebaseAuth.getInstance();
            loginTextView = (TextView)findViewById(R.id.loginText);
            passwordEditText = (EditText)findViewById(R.id.passwordField);
            emailEditText = (EditText)findViewById(R.id.emailField);
            signUpButton = (Button)findViewById(R.id.signupButton);
            sign_up_rbAdmin = (RadioButton)findViewById(R.id.sign_up_rbAdmin);
            sign_up_rbStudent = (RadioButton)findViewById(R.id.sign_up_rbStudent);

            database = FirebaseDatabase.getInstance();
            ref = database.getReference("users");






            loginTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String password = passwordEditText.getText().toString();
                    String email = emailEditText.getText().toString();

                    password = password.trim();
                    email = email.trim();

                    if (password.isEmpty() || email.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage(R.string.signup_error_message)
                                .setTitle(R.string.signup_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            registerUser();

                                            switch (v.getId()){
                                                case R.id.signupButton:
                                                    if(sign_up_rbAdmin.isChecked()){
                                                        userType = "Admin";
                                                    }

                                                    if(sign_up_rbStudent.isChecked()){
                                                        userType = "Student";
                                                    }
                                            }

                                            ref.child(uid).child("userType").setValue(userType);
                                            Log.d(TAG, "setType: "+userType);



                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                                            startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                            builder.setMessage(task.getException().getMessage())
                                                    .setTitle(R.string.login_error_title)
                                                    .setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    }
                                });
                    }
                }
            });
        }

        private void registerUser(){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();
            uid = firebaseUser.getUid();
            username = emailEditText.getText().toString();
            score = 0.0;
            userType = "null";
            status = "Online";
            image = "kaospdksad";
            email = emailEditText.getText().toString();

            ref.child(uid).setValue(user);
            user = new User(username,uid,email,userType,score,status,image,latitude,longitude);

            Log.d(TAG, "registerUser: "+user);

//            if(sign_up_rbAdmin.isChecked()){
//                userType = "Admin";
//                ref.child(uid).child("userType").setValue(userType);
//            }
//            else{
//                userType = "Student";
//                ref.child(uid).child("userType").setValue(userType);
//            }




            ref.child(uid).setValue(user);


            Toast.makeText(this,username,Toast.LENGTH_LONG).show();



        }



}
