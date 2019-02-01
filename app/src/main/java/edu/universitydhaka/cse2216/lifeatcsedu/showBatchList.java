package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;

public class showBatchList extends Activity {
    private DatabaseReference batchPageDatabaseReference;
    private DatabaseReference userPageDatabaseReference;
    private ArrayList<String>mBatch = new ArrayList<>();

    String nowUser;
    User user;
    BadgeView AddStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_batch_list);
        AddStudentButton = findViewById(R.id.AddStudent);
        AddStudentButton.setVisibility(GONE);
        nowUser=getIntent().getStringExtra("current");
        userPageDatabaseReference = FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));
        batchPageDatabaseReference = FirebaseDatabase.getInstance().getReference("student");

        userPageDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    System.out.println("habijabi");
                    AddStudentButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AddStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showBatchList.this,addStudent.class);
                startActivity(intent);
            }

        });


        batchPageDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBatch.clear();
                int i=0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    mBatch.add(ds.getKey());
                    i++;
                }
                Collections.reverse(mBatch);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view4);
        batchListRecyclerViewAdapter adapter = new batchListRecyclerViewAdapter(this,mBatch);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
