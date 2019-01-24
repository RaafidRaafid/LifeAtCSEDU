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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showResourceList extends Activity {

    private DatabaseReference resourcesDatabaseRef;
    private DatabaseReference userDatabase;
    private FirebaseAuth userAuth;
    private FirebaseUser userAuthUser;

    private ArrayList<String> resourceskTitles = new ArrayList<>();
    private ArrayList<String> resourcesLinks = new ArrayList<>();

    String nowUser,courseCode;
    User user;

    BadgeView addResourceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_resource_list);

        courseCode = getIntent().getStringExtra("courseCode");
        resourcesDatabaseRef = FirebaseDatabase.getInstance().getReference("resources/"+courseCode);
        addResourceButton=findViewById(R.id.addResource);
        addResourceButton.setVisibility(View.GONE);
        userAuth = FirebaseAuth.getInstance();
        userAuthUser = userAuth.getCurrentUser();
        nowUser= userAuthUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    addResourceButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addResourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showResourceList.this,addResourcePage.class);
                intent.putExtra("courseCode",courseCode);
                startActivity(intent);
            }

        });

        resourcesDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int x=0;

                resourceskTitles.clear();
                resourcesLinks.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    System.out.println("ou dekho " + x); x++;
                    Resource resource = ds.getValue(Resource.class);
                    if(resource.getCourseCode().equals(courseCode)){
                        resourceskTitles.add(resource.getTitle());
                        resourcesLinks.add(resource.getLink());
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view_Resource);
        resourceRecyclerViewAdapter adapter = new resourceRecyclerViewAdapter(this,resourceskTitles,resourcesLinks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
