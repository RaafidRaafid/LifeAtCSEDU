package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showCourseList extends Activity {

    private DatabaseReference coursePageDataBaseRef;
    private DatabaseReference userDatabaseRef;

    private ArrayList<String> titleNames = new ArrayList<>();
    private ArrayList<String> courseCodes = new ArrayList<>();

    String semester,nowUser;

    Button addCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_course_list);

        addCourseButton = findViewById(R.id.AddCourse);
        addCourseButton.setVisibility(View.GONE);

        nowUser = getIntent().getStringExtra("current");
        semester = getIntent().getStringExtra("semester");

        System.out.println("user/"+nowUser.replace('.','&') + " " + semester);

        coursePageDataBaseRef = FirebaseDatabase.getInstance().getReference("courses");
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    addCourseButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        coursePageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                titleNames.clear();
                courseCodes.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Course course = ds.getValue(Course.class);
                    if(course.getCode().substring(course.getCode().length()-4,course.getCode().length()-2).equals(semester)){
                        titleNames.add(course.getTitle());
                        courseCodes.add(course.getCode());
                    }
                }

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(showCourseList.this,addCoursePage.class));
            }
        });
    }

    public void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view_course);
        courseRecyclerViewAdapter adapter = new courseRecyclerViewAdapter(this,titleNames,courseCodes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
