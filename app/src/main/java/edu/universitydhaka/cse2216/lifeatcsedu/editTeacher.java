package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class editTeacher extends Activity {
    private EditText editTeacherName;
    private EditText editTeacherPhone;
    private EditText editTeacherEmail;
    private EditText editTeacherDesignation;
    private EditText editTeacherResearchArea;
    private Button editTeacherSubmitButton;

    private DatabaseReference teacherDatabaseRef;

    Teacher teacher;
    String key;
    String designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_teacher);

        editTeacherName =(EditText) findViewById(R.id.editTeacherName);
        editTeacherPhone = (EditText) findViewById(R.id.editTeacherPhone);
        editTeacherEmail = (EditText) findViewById(R.id.editTeacherEmail);
        editTeacherResearchArea = (EditText) findViewById(R.id.editTeacherResearchArea);
        editTeacherSubmitButton=(Button) findViewById(R.id.editTeacherSubmitButton);
        editTeacherSubmitButton.setVisibility(GONE);

        key = getIntent().getStringExtra("showKey");
        designation=getIntent().getStringExtra("designation");
        teacherDatabaseRef = FirebaseDatabase.getInstance().getReference("teacher/"+ designation+ "/" + key);

        teacherDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);

                editTeacherName.setText(teacher.getName());
                editTeacherEmail.setText(teacher.getEmail());
                editTeacherPhone.setText(teacher.getPhone());
                //editTeacherDesignation.setText(teacher.getDesignation());
                editTeacherResearchArea.setText(teacher.getResearchArea());
                editTeacherSubmitButton.setVisibility(View.VISIBLE);
                teacherDatabaseRef.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editTeacherSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditTeacher();
            }
        });
    }

    private void goToEditTeacher() {
        Teacher changedTeacher = teacher;
        changedTeacher.setName(editTeacherName.getText().toString().trim());
        changedTeacher.setEmail(editTeacherEmail.getText().toString().trim());
        changedTeacher.setPhone(editTeacherPhone.getText().toString().trim());
        //changedTeacher.setDesignation(editTeacherDesignation.getText().toString().trim());
        changedTeacher.setResearchArea(editTeacherResearchArea.getText().toString().trim());
        teacherDatabaseRef.setValue(changedTeacher).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(editTeacher.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(this, showSingleTeacher.class);
        intent.putExtra("showKey",key);
        intent.putExtra("designation",designation);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
