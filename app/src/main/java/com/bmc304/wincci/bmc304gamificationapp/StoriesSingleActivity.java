package com.bmc304.wincci.bmc304gamificationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class StoriesSingleActivity extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;
    private ImageView mPostSingleImage;
    private TextView mPostSingleTitle;
    private TextView mPostSingleDesc;
    private TextView mSingleRemoveBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_single);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key = getIntent().getExtras().getString("post_id");
        mAuth = FirebaseAuth.getInstance();

        mPostSingleDesc=(TextView)findViewById(R.id.singlePostDesc);
        mPostSingleImage = (ImageView)findViewById(R.id.singlePostImage);
        mPostSingleTitle = (TextView)findViewById(R.id.singlePostTitle);
        mSingleRemoveBtn = (Button)findViewById(R.id.remove_post);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();
                String post_uid = (String)dataSnapshot.child("uid").getValue();

                mPostSingleTitle.setText(post_title);
                mPostSingleDesc.setText(post_desc);

                Picasso.with(StoriesSingleActivity.this).load(post_image).into(mPostSingleImage);
                if(mAuth.getCurrentUser().getUid().equals(post_uid)){
                    mSingleRemoveBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              mDatabase.child(mPost_key).removeValue();
              Intent mainIntent = new Intent(StoriesSingleActivity.this,MainActivity.class);
              startActivity(mainIntent);
            }
        });

        Toast.makeText(StoriesSingleActivity.this,mPost_key,Toast.LENGTH_SHORT).show();
    }
}
