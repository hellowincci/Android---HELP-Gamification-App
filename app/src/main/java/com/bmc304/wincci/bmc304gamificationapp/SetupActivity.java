package com.bmc304.wincci.bmc304gamificationapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private static final String TAG = "Setup";
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;

    private FirebaseUser mCurrentUser;
    private DatabaseReference mUserDatabase;
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private Button mStatusButton;
    private Button mChangeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);
        mName = (TextView) findViewById(R.id.settings_name);
        mStatus = (TextView) findViewById(R.id.settings_status);
        mStatusButton = (Button) findViewById(R.id.settings_status_btn);
        mChangeImageButton = (Button) findViewById(R.id.settings_image_btn);

        mCurrentUser =FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        Log.d(TAG,"current_uid"+current_uid);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,dataSnapshot.getValue(String.class));
                String name = dataSnapshot.child("username").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                mName.setText(name);
                mStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//                String uid = dataSnapshot.child("userID").getValue().toString();
//                String image = dataSnapshot.child("image").getValue().toString();
//                String status  = dataSnapshot.child("status").getValue().toString();
//                String score = dataSnapshot.child("score").getValue().toString();
//                mStatus.setText(status);


//        mSubmiBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startSetupAccount();
//            }
//        });
//        mSetupImageBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent galleryIntent = new Intent();
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent,GALLERY_REQUEST);
//
//
//            }
//        });
//    }
//
//    private void startSetupAccount(){
//        String name = mName.getText().toString().trim();
//        String user_id = mAuth.getCurrentUser().getUid();
//        if(!TextUtils.isEmpty(name)){
//            mProgressDialog.setMessage("Finishing account setup...");
//            mProgressDialog.show();
//            mDatabaseUsers.child(user_id).child("name").setValue(name);
//
//            mProgressDialog.dismiss();
//            Intent adminIntent = new Intent(SetupActivity.this,AdminActivity.class);
//            adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(adminIntent);
//
//        }
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode,int resultCode,Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//
//        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
//            Uri imageUri = data.getData();
//            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
//            mSetupImageBtn.setImageURI(imageUri);
//        }
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if(resultCode == RESULT_OK){
//                mImageUri = result.getUri();
//                mSetupImageBtn.setImageURI(mImageUri);
//
//            }
//            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Exception error = result.getError();
//            }
//        }
    }
}
