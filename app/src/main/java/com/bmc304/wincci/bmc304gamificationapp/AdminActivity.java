package com.bmc304.wincci.bmc304gamificationapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(AdminActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }

        };
        mAuth.addAuthStateListener(mAuthListener);

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.admin_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//
//
//    }
//
//    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.action_add){
//            startActivity(new Intent(AdminActivity.this,PostActivity.class));
//        }
//        if(item.getItemId()==R.id.action_logout){
//            logout();
//        }
//
//        if(item.getItemId() == R.id.action_settings){
//            startActivity(new Intent(AdminActivity.this,SetupActivity.class));
//        }
        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        mAuth.signOut();
//
//    }
    }
}
