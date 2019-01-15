package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSingleNotice extends Activity {
    private DatabaseReference showSingleNoticeDatabaseRef;

    TextView noticeTitle;
    TextView noticeDesc;
    TextView noticeTime;

    String key;
    Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_notice);

        noticeTitle = findViewById(R.id.title_value);
        noticeDesc = findViewById(R.id.desc_value);
        noticeTime = findViewById(R.id.time_value);


        key = getIntent().getStringExtra("showKey");
        showSingleNoticeDatabaseRef = FirebaseDatabase.getInstance().getReference("notice/" +key);

        showSingleNoticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notice = dataSnapshot.getValue(Notice.class);
                noticeTitle.setText(notice.getTitle());
                noticeDesc.setText(notice.getDescription());
                noticeTime.setText(notice.getTime().substring(0,10).replace('-','/')
                        + " at "
                        +notice.getTime().substring(11).replace('-',':'));



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
