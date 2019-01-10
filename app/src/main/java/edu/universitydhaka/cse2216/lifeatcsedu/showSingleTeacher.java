package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSingleTeacher extends Activity {

    private DatabaseReference showSingleTeacherDatabaseRef;

    TextView teacherName;
    TextView teacherPhone;
    TextView teacherEmail;
    TextView teacherDesignation;
    TextView teacherResearchArea;


    String key;
    String designation;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_teacher);

        teacherName = findViewById(R.id.teacherName_value);
        teacherPhone = findViewById(R.id.teacherPhone_value);
        teacherEmail =findViewById(R.id.teacherEmail_value);
        teacherDesignation = findViewById(R.id.teacherDesignation_value);
        teacherResearchArea = findViewById(R.id.teacherResearchArea_value);


        key = getIntent().getStringExtra("showKey");
        designation=getIntent().getStringExtra("designation");
        showSingleTeacherDatabaseRef = FirebaseDatabase.getInstance().getReference("teacher/"+ designation+ "/" + key);


        showSingleTeacherDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                teacherName.setText(teacher.getName());
                teacherPhone.setText(teacher.getPhone());
                teacherEmail.setText(teacher.getEmail());
                teacherDesignation.setText(teacher.getDesignation());
                teacherResearchArea.setText(teacher.getResearchArea());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
