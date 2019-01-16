package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class showSingleStuff extends Activity {

    private DatabaseReference showSingleStuffDatabaseRef;

    TextView stuffName;
    TextView stuffPhoneNo;
    TextView stuffWork;
    TextView stuffEmail;

    Button editStaffButton;
    Button deleteStaffButton;

    private FirebaseAuth loginAuthentication;
    private DatabaseReference userDatabase;

    String key;
    Stuff stuff;
    String nowUser;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_stuff);

        editStaffButton = findViewById(R.id.Edit_staff_button);
        editStaffButton.setVisibility(GONE);
        deleteStaffButton = findViewById(R.id.Delete_staff_button);
        deleteStaffButton.setVisibility(GONE);

        stuffName = findViewById(R.id.stuffName_value);
        stuffPhoneNo = findViewById(R.id.phoneNo_value);
        stuffWork = findViewById(R.id.work_value);
        stuffEmail =findViewById(R.id.stuffEmail_value);

        loginAuthentication = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = loginAuthentication.getCurrentUser();
        nowUser=currentUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));


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

                showSingleStuffDatabaseRef.removeEventListener(this);


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
                    editStaffButton.setVisibility(View.VISIBLE);
                    deleteStaffButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToEditStaff(v);
            }

        });

        deleteStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleStuffDatabaseRef.removeValue();
                finish();
            }
        });

    }

    private void takeToEditStaff(View v) {
        Intent intent = new Intent(this,editStaff.class);
        intent.putExtra("showKey",key);
        startActivity(intent);
    }
}
