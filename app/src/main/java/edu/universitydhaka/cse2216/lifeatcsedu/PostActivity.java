package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends Activity {

    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitButton;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("notice");
        mPostTitle=(EditText) findViewById(R.id.editText);
        mPostDesc= (EditText) findViewById(R.id.editText2);
        mSubmitButton=(Button) findViewById(R.id.button);
        mProgress= new ProgressDialog(this);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }
    private void startPosting() {
        mProgress.setMessage("Posting");
        mProgress.show();
        String title_val=mPostTitle.getText().toString().trim();
        String desc_val=mPostDesc.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateAndTime = sdf.format(new Date());
        //System.out.println(currentDateAndTime);

        if(!TextUtils.isEmpty(title_val)&& !TextUtils.isEmpty(desc_val)){
            DatabaseReference newPost=mDatabase.push();
            notice = new Notice(title_val,desc_val,currentDateAndTime,newPost.getKey());
            mDatabase.child(newPost.getKey()).setValue(notice);
            mProgress.dismiss();
            //startActivity(new Intent(PostActivity.this,noticeShowPage.class));
            finish();

        }
    }
}
