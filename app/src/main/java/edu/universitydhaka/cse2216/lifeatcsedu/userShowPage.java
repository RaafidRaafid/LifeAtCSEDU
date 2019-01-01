package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class userShowPage extends Activity {

    private DatabaseReference userPageDataBaseRef;
    private FirebaseAuth userPageAuth;

    private ArrayList<String> mImageURL = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageEmails = new ArrayList<>();

    String nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_page);

        userPageDataBaseRef = FirebaseDatabase.getInstance().getReference("users");
        userPageAuth = FirebaseAuth.getInstance();

        nowUser = getIntent().getStringExtra("current");

        userPageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mImageEmails.clear();
                mImageNames.clear();
                mImageURL.clear();

                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    mImageNames.add(user.getName());
                    mImageURL.add("https://i.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg");
                    mImageEmails.add(user.getEmail());
                    String akak = user.getEmail();

                    if(akak.equals(nowUser)){
                        Collections.swap(mImageNames,0,i);
                        Collections.swap(mImageURL,0,i);
                        Collections.swap(mImageEmails,0,i);
                    }
                    i++;
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        userListRecyclerViewAdapter adapter = new userListRecyclerViewAdapter(this,mImageURL,mImageNames,mImageEmails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

// https://i.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg