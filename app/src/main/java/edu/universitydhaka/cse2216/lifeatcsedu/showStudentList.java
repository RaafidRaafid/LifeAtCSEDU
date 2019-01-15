package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class showStudentList extends Activity {
    private DatabaseReference studentPageDataBaseRef;
    private ArrayList<String>mStudentName = new ArrayList<>();
    private ArrayList<String>mStudentKey = new ArrayList<>();
    private ArrayList<String>mStudentBatch =new ArrayList<>();

    String batch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);
        batch = getIntent().getStringExtra("showKey");
        studentPageDataBaseRef = FirebaseDatabase.getInstance().getReference("student").child(batch);

        studentPageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mStudentName.clear();
                mStudentKey.clear();
                mStudentBatch.clear();
                int i=0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Student student = ds.getValue(Student.class);
                    mStudentName.add(student.getName());
                    mStudentBatch.add(student.getBatch());
                    mStudentKey.add(ds.getKey());
                }
                Collections.reverse(mStudentKey);
                Collections.reverse(mStudentName);
                Collections.reverse(mStudentBatch);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view5);
        studentListRecyclerViewAdapter adapter = new studentListRecyclerViewAdapter(this,mStudentName,mStudentBatch,mStudentKey);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
