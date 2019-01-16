package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class showSingleTeacher extends Activity {

    private DatabaseReference showSingleTeacherDatabaseRef;

    TextView teacherName;
    TextView teacherPhone;
    TextView teacherEmail;
    TextView teacherDesignation;
    TextView teacherResearchArea;

    Button editTeacherButton;
    Button deleteTeacherButton;

    private FirebaseAuth loginAuthentication;
    private DatabaseReference userDatabase;


    String key;
    String designation;
    String nowUser;

    User user;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_teacher);

        editTeacherButton = findViewById(R.id.Edit_teacher_button);
        editTeacherButton.setVisibility(GONE);
        deleteTeacherButton = findViewById(R.id.Delete_teacher_button);
        deleteTeacherButton.setVisibility(GONE);

        teacherName = findViewById(R.id.teacherName_value);
        teacherPhone = findViewById(R.id.teacherPhone_value);
        teacherEmail =findViewById(R.id.teacherEmail_value);
        teacherDesignation = findViewById(R.id.teacherDesignation_value);
        teacherResearchArea = findViewById(R.id.teacherResearchArea_value);

        loginAuthentication = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = loginAuthentication.getCurrentUser();
        nowUser=currentUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


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

                showSingleTeacherDatabaseRef.removeEventListener(this);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    System.out.println("habijabi");
                    editTeacherButton.setVisibility(View.VISIBLE);
                    deleteTeacherButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToEditTeacher(v);
            }

        });

        deleteTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleTeacherDatabaseRef.removeValue();
                finish();
            }
        });
    }

    private void takeToEditTeacher(View v) {
        Intent intent = new Intent(this,editTeacher.class);
        intent.putExtra("showKey",key);
        intent.putExtra("designation",designation);
        startActivity(intent);
    }
}
