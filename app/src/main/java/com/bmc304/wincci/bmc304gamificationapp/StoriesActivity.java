package com.bmc304.wincci.bmc304gamificationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

/**
 * Created by wincci on 10/3/2017.
 */

public class StoriesActivity extends Fragment {
    private RecyclerView mStories;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLike;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private boolean mProcessLike = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stories,container,false);
        mStories = (RecyclerView) view.findViewById(R.id.stories_list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabase.keepSynced(true);
        mDatabaseLike.keepSynced(true);
        mStories.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        mStories.setLayoutManager(layoutManager);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginIntent = new Intent(getActivity(),SignUpActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }

        };
        mAuth.addAuthStateListener(mAuthListener);

        return view;
    }

    @Override
    public void onStart(){
            super.onStart();

        FirebaseRecyclerAdapter<Stories,StoriesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Stories, StoriesViewHolder>(
                Stories.class,R.layout.stories_card_layout,StoriesViewHolder.class,mDatabase

        ) {


            @Override
            protected void populateViewHolder(StoriesViewHolder viewHolder, Stories model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getContext(),model.getImage());
                viewHolder.setUsername(model.getUsername());

                viewHolder.setLikebtn(post_key);

                viewHolder.mView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent singleStoriesIntent = new Intent(getActivity(),StoriesSingleActivity.class);
                        singleStoriesIntent.putExtra("post_id",post_key);
                        startActivity(singleStoriesIntent);
                    }



                });

                viewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcessLike = true;

                            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (mProcessLike) {
                                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {

                                            mDatabaseLike.child(post_key).child(mAuth
                                                    .getCurrentUser().getUid()).removeValue();
                                            mProcessLike = false;

                                        } else {
                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("RandomValue");
                                            mProcessLike = false;
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    }
                });


            }
        };
        mStories.setAdapter(firebaseRecyclerAdapter);

        }

    public static class StoriesViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton mLikebtn;
        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;

        public StoriesViewHolder(View itemView) {
            super(itemView);
            mView  = itemView;
            mLikebtn = (ImageButton) mView.findViewById(R.id.likeBtn);
            mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
            mAuth = FirebaseAuth.getInstance();
            mDatabaseLike.keepSynced(true);

        }

        public void setLikebtn(final String post_key){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                        Log.d(TAG, "onDataChange: Getcurrentuser"+mAuth.getCurrentUser().getUid());
                        mLikebtn.setImageResource(R.drawable.like_red);
                    }else{
                        mLikebtn.setImageResource(R.drawable.like_black);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void setTitle(String title){
           TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc){
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);

        }

        public void setImage(Context ctx, String image){
            ImageView stories_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(stories_image);

        }

        public void setUsername(String username){
            TextView post_username = (TextView) mView.findViewById(R.id.post_username);
            post_username.setText(username);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
         //Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
