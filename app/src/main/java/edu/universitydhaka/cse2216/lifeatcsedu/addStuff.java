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

import java.text.SimpleDateFormat;
import java.util.Date;

public class addStuff extends Activity {
    private EditText mStuffName;
    private EditText mStuffPhone;
    private EditText mStuffWork;
    private EditText mStuffEmail;
    private Button mSubmitButton;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    Stuff stuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("stuff");
        mStuffName =(EditText) findViewById(R.id.addStuffName);
        mStuffPhone = (EditText) findViewById(R.id.addStuffPhone);
        mStuffWork = (EditText) findViewById(R.id.addStuffWork);
        mStuffEmail = (EditText) findViewById(R.id.addStuffEmail);
        mSubmitButton=(Button) findViewById(R.id.submitButton2);
        mProgress= new ProgressDialog(this);
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
        String name_val=mStuffName.getText().toString().trim();
        String phone_val=mStuffPhone.getText().toString().trim();
        String work_val=mStuffWork.getText().toString().trim();
        String email_val=mStuffEmail.getText().toString().trim();


        if(!TextUtils.isEmpty(name_val)&& !TextUtils.isEmpty(phone_val)&& !TextUtils.isEmpty(work_val)&& !TextUtils.isEmpty(email_val) ){
            DatabaseReference newStuff=mDatabase.push();
            stuff = new Stuff(name_val,phone_val,work_val,email_val,newStuff.getKey());
            mDatabase.child(newStuff.getKey()).setValue(stuff);
            mProgress.dismiss();
            //startActivity(new Intent(addPost.this,noticeShowPage.class));
            finish();

        }
    }

}
