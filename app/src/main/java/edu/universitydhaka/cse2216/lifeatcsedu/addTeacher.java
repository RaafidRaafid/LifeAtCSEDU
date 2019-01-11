package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addTeacher extends Activity {

    private EditText mTeacherName;
    private EditText mTeacherPhone;
    private EditText mTeacherEmail;
    private EditText mTeacherDesignation;
    private EditText mTeacherResearchArea;
    private Button mSubmitButton;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    Teacher teacher;
    String teacherType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);

        mTeacherName =(EditText) findViewById(R.id.editTeacherName);
        mTeacherPhone = (EditText) findViewById(R.id.editTeacherPhone);
        mTeacherEmail = (EditText) findViewById(R.id.editTeacherEmail);
        mTeacherResearchArea = (EditText) findViewById(R.id.editTeacherResearchArea);
        mSubmitButton=(Button) findViewById(R.id.submitButton3);
        mProgress= new ProgressDialog(this);
        teacherType = getIntent().getStringExtra("teacherType");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("teacher").child(teacherType);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAdding();
            }
        });
    }
    private void startAdding() {
        mProgress.setMessage("Adding");
        mProgress.show();
        String name_val=mTeacherName.getText().toString().trim();
        String phone_val=mTeacherPhone.getText().toString().trim();
        String email_val=mTeacherEmail.getText().toString().trim();
        String designation_val=teacherType;
        String researchArea_val=mTeacherResearchArea.getText().toString().trim();


        if(!TextUtils.isEmpty(name_val)&& !TextUtils.isEmpty(phone_val)&& !TextUtils.isEmpty(email_val)&& !TextUtils.isEmpty(designation_val)&& !TextUtils.isEmpty(researchArea_val) ){
            DatabaseReference newTeacher=mDatabase.push();
            teacher = new Teacher(name_val,phone_val,email_val,designation_val,researchArea_val,newTeacher.getKey());
            mDatabase.child(newTeacher.getKey()).setValue(teacher);
            mProgress.dismiss();
            //startActivity(new Intent(addPost.this,noticeShowPage.class));
            finish();

        }
    }
}
