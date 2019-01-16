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

public class addStudent extends Activity {

    private EditText mStudentName;
    private EditText mStudentRoll;
    private EditText mStudentBatch;
    private EditText mStudentPhone;
    private EditText mStudentEmail;
    private EditText mStudentRegistrationNo;
    private Button mSubmitButton;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        mStudentName =(EditText) findViewById(R.id.addStudentName);
        mStudentBatch = (EditText) findViewById(R.id.addStudentBatch);
        mStudentRoll = (EditText) findViewById(R.id.addStudentRoll);
        mStudentPhone = (EditText) findViewById(R.id.addStudentPhone);
        mStudentEmail = (EditText) findViewById(R.id.addStudentEmail);
        mStudentRegistrationNo = (EditText) findViewById(R.id.addStudentRegistrationNo);
        mSubmitButton=(Button) findViewById(R.id.submitButton4);
        mProgress= new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("student");

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
        String name_val=mStudentName.getText().toString().trim();
        String batch_val=mStudentBatch.getText().toString().trim();
        String roll_val=mStudentRoll.getText().toString().trim();
        String phone_val=mStudentPhone.getText().toString().trim();
        String email_val=mStudentEmail.getText().toString().trim();
        String registrationNo_val=mStudentRegistrationNo.getText().toString().trim();


        if(!TextUtils.isEmpty(name_val)&& !TextUtils.isEmpty(batch_val)&& !TextUtils.isEmpty(roll_val)&& !TextUtils.isEmpty(phone_val)&& !TextUtils.isEmpty(email_val)&& !TextUtils.isEmpty(registrationNo_val) ){
            DatabaseReference newStudent=mDatabase.child(batch_val).push();
            student = new Student(name_val,batch_val,roll_val, phone_val,email_val,registrationNo_val);
            newStudent.setValue(student);
            mProgress.dismiss();
            finish();

        }
    }
}
