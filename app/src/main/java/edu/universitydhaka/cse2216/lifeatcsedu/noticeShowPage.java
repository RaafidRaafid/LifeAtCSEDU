package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class noticeShowPage extends Activity {
    private DatabaseReference noticePageDataBaseRef;
    private DatabaseReference userDatabase;

    private ArrayList<String> mNoticeTitles = new ArrayList<>();
    private ArrayList<String> mNoticeTime = new ArrayList<>();
    private ArrayList<String> mNoticeKey = new ArrayList<>();

    String nowUser;
    User user;

    BadgeView AddPostButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_show_page);

        noticePageDataBaseRef = FirebaseDatabase.getInstance().getReference("notice");
        AddPostButton=findViewById(R.id.AddPost);
        AddPostButton.setVisibility(View.GONE);
        nowUser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        System.out.println("users/"+nowUser.replace('.','&'));
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    System.out.println("habijabi");
                    AddPostButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(noticeShowPage.this,addPost.class));
            }

        });

        noticePageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mNoticeTitles.clear();
                mNoticeTime.clear();
                mNoticeKey.clear();

                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Notice notice = ds.getValue(Notice.class);
                    mNoticeTitles.add(notice.getTitle());
                    mNoticeTime.add(notice.getTime());
                    mNoticeKey.add(notice.getNoticeKey());
                    i++;
                }
                Collections.reverse(mNoticeTitles);
                Collections.reverse(mNoticeTime);
                Collections.reverse(mNoticeKey);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        noticeListRecyclerViewAdapter adapter = new noticeListRecyclerViewAdapter(this,mNoticeTitles,mNoticeTime,mNoticeKey);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
