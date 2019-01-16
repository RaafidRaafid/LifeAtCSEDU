package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class showSingleNotice extends Activity {
    private DatabaseReference showSingleNoticeDatabaseRef;

    TextView noticeTitle;
    TextView noticeDesc;
    TextView noticeTime;

    Button editNoticeButton;
    Button deleteNoticeButton;

    String key;
    Notice notice;
    private FirebaseAuth loginAuthentication;
    private DatabaseReference userDatabase;
    String nowUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_notice);

        editNoticeButton = findViewById(R.id.Edit_notice_button);
        editNoticeButton.setVisibility(GONE);
        deleteNoticeButton = findViewById(R.id.Delete_notice_button);
        deleteNoticeButton.setVisibility(GONE);
        noticeTitle = findViewById(R.id.title_value);
        noticeDesc = findViewById(R.id.desc_value);
        noticeTime = findViewById(R.id.time_value);
        loginAuthentication = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = loginAuthentication.getCurrentUser();
        nowUser=currentUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


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


                showSingleNoticeDatabaseRef.removeEventListener(this);
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
                    editNoticeButton.setVisibility(View.VISIBLE);
                    deleteNoticeButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToEditNotice(v);
            }

        });

        deleteNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleNoticeDatabaseRef.removeValue();
                finish();
            }
        });
    }



    private void takeToEditNotice(View view) {
        Intent intent = new Intent(this,editNotice.class);
        intent.putExtra("showKey",key);
        startActivity(intent);
    }
}
