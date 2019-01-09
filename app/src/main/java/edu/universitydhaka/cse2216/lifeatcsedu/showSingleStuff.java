package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSingleStuff extends Activity {

    private DatabaseReference showSingleStuffDatabaseRef;

    TextView stuffName;
    TextView stuffPhoneNo;
    TextView stuffWork;
    TextView stuffEmail;

    String key;
    Stuff stuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_stuff);

        stuffName = findViewById(R.id.stuffName_value);
        stuffPhoneNo = findViewById(R.id.phoneNo_value);
        stuffWork = findViewById(R.id.work_value);
        stuffEmail =findViewById(R.id.stuffEmail_value);


        key = getIntent().getStringExtra("showKey");
        showSingleStuffDatabaseRef = FirebaseDatabase.getInstance().getReference("stuff/" +key);

        showSingleStuffDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stuff = dataSnapshot.getValue(Stuff.class);
                stuffName.setText(stuff.getName());
                stuffPhoneNo.setText(stuff.getPhoneNo());
                stuffWork.setText(stuff.getWork());
                stuffEmail.setText(stuff.getEmail());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
