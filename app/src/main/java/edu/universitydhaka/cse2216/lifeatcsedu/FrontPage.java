package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class FrontPage extends Activity {

    FirebaseAuth currentlyLoggedIn;
    DatabaseReference userDatabase;

    ImageButton toUser;
    ImageButton toNewsfeed;
    ImageButton toPeople;
    ImageButton toStudy;
    ImageButton toQA;

    String nowUser,token;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        currentlyLoggedIn = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentlyLoggedIn.getCurrentUser().getEmail().replace('.','&')).child("token");
        //FirebaseUser currentUser = currentlyLoggedIn.getCurrentUser();

        toUser = findViewById(R.id.toUser);
        toNewsfeed = findViewById(R.id.toNewsfeed);
        toPeople = findViewById(R.id.toPeople);
        toStudy = findViewById(R.id.toStudy);
        toQA = findViewById(R.id.toQA);
        logout = findViewById(R.id.logout_button);

        nowUser = getIntent().getStringExtra("current");

        toUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToUsers();
            }
        });
        toNewsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToNewsfeed();
            }
        });
        toStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToStudy();
            }
        });
        toQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taketoOA();
            }
        });
        toPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToPeople(v);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentlyLoggedIn.signOut();
                Intent intent = new Intent(FrontPage.this,LoginPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        token = FirebaseInstanceId.getInstance().getToken();
        userDatabase.setValue(token);

    }


    public void takeToUsers(){
        Intent intent = new Intent(this,showUserList.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }
    public void takeToNewsfeed(){
        Intent intent = new Intent(this,noticeShowPage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    public void takeToStudy(){
        Intent intent = new Intent(this,showSemesterLIst.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    public void taketoOA(){
        Intent intent = new Intent(this,showQAList.class);
        //intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    private void takeToPeople(View view) {
        Intent intent = new Intent(this,PeoplePage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

}
