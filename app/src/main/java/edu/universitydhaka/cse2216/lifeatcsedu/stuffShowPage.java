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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class stuffShowPage extends Activity {

    private DatabaseReference stuffPageDataBaseRef;
    private DatabaseReference userDatabase;

    private ArrayList<String> mStuffEmail = new ArrayList<>();
    private ArrayList<String> mStuffName = new ArrayList<>();
    private ArrayList<String> mStuffKey = new ArrayList<>();

    String nowUser;
    User user;

    BadgeView AddStuffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_show_page);

        stuffPageDataBaseRef = FirebaseDatabase.getInstance().getReference("stuff");
        AddStuffButton=findViewById(R.id.AddStaff);
        AddStuffButton.setVisibility(View.GONE);
        nowUser=getIntent().getStringExtra("current");
        System.out.println("users/"+nowUser.replace('.','&'));
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    System.out.println("habijabi");
                    AddStuffButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AddStuffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(stuffShowPage.this,addStuff.class));
            }

        });

        stuffPageDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mStuffEmail.clear();
                mStuffName.clear();
                mStuffKey.clear();

                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Stuff stuff = ds.getValue(Stuff.class);
                    mStuffName.add(stuff.getName());
                    mStuffEmail.add(stuff.getEmail());
                    mStuffKey.add(stuff.getStuffKey());
                    i++;
                    //dsfsfsd
                }
                Collections.reverse(mStuffName);
                Collections.reverse(mStuffEmail);
                Collections.reverse(mStuffKey);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view2);
        stuffListRecyclerViewAdapter adapter = new stuffListRecyclerViewAdapter(this,mStuffName,mStuffEmail,mStuffKey);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
