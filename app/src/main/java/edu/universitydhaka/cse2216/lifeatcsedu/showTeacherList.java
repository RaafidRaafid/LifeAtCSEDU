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

public class showTeacherList extends Activity {
    private DatabaseReference teacherPageDataBaseRef;
    private DatabaseReference userDatabase;

    private ArrayList<String> mTeacherDesignation = new ArrayList<>();
    private ArrayList<String> mTeacherName = new ArrayList<>();
    private ArrayList<String> mTeacherKey = new ArrayList<>();

    String nowUser,teacherType;

    User user;

    BadgeView AddTeacherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_show_page);

        AddTeacherButton=findViewById(R.id.AddTeacher);
        AddTeacherButton.setVisibility(View.GONE);
        nowUser=getIntent().getStringExtra("current");
        teacherType=getIntent().getStringExtra("teacherType");
        teacherPageDataBaseRef = FirebaseDatabase.getInstance().getReference("teacher/"+teacherType);

        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    System.out.println("habijabi");
                    AddTeacherButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AddTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showTeacherList.this,addTeacher.class);
                intent.putExtra("teacherType",teacherType);
                startActivity(intent);
            }

        });

        teacherPageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mTeacherDesignation.clear();
                mTeacherName.clear();
                mTeacherKey.clear();

                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Teacher teacher = ds.getValue(Teacher.class);
                    mTeacherName.add(teacher.getName());
                    mTeacherDesignation.add(teacher.getDesignation());
                    mTeacherKey.add(teacher.getTeacherKey());
                    i++;
                }
                Collections.reverse(mTeacherName);
                Collections.reverse(mTeacherDesignation);
                Collections.reverse(mTeacherKey);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view3);
        teacherListRecyclerViewAdapter adapter = new teacherListRecyclerViewAdapter(this,mTeacherName,mTeacherDesignation,mTeacherKey);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
