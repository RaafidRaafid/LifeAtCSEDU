package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class addPost extends Activity {

    private EditText mPostTitle;
    private EditText mPostDesc;
    private EditText mfor;
    private Button mSubmitButton;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    Notice notice;

    DatabaseReference userDatabase;

    String token;
    ArrayList<String> tokenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("notice");
        mPostTitle=(EditText) findViewById(R.id.editText);
        mPostDesc= (EditText) findViewById(R.id.editText2);
        mSubmitButton=(Button) findViewById(R.id.button);
        mfor = findViewById(R.id.add_post_for);
        mProgress= new ProgressDialog(this);
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }
    private void startPosting() {

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tokenList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);

                    token = user.getToken();
                    //if(!token.equals("-1")) tokenList.add(token);
                    if(!token.equals("-1")){
                        if(mfor.getText().toString().trim().equals("null")) tokenList.add(token);
                        else{
                            if(mfor.getText().toString().trim().equals(user.getBatch())) tokenList.add(token);
                        }
                    }

                }

                new Notify().execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
            //startActivity(new Intent(addPost.this,noticeShowPage.class));
            finish();

        }
    }

    public class Notify extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {


            for(String x : tokenList){
                try {

                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization","key=AIzaSyBDzFhd428wK5TH9xedVUOmlvtjKPCV2Oc");
                    conn.setRequestProperty("Content-Type", "application/json");

                    JSONObject json = new JSONObject();

                    json.put("to", x);


                    JSONObject info = new JSONObject();
                    info.put("title", mPostTitle.getText().toString().trim());   // Notification title
                    info.put("body", "New Notice for YOU!!"); // Notification body

                    json.put("notification", info);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json.toString());
                    wr.flush();
                    conn.getInputStream();

                    System.out.println("eita kisu hoilo bolo to " + x);

                }
                catch (Exception e)
                {
                    Log.d("Error",""+e);
                }
            }


            return null;
        }
    }
}
