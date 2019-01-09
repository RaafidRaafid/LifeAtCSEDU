package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontPage extends Activity {

    FirebaseAuth currentlyLoggedIn;

    ImageButton toUser;
    ImageButton toNewsfeed;
    ImageButton toPeople;
    ImageButton toStudy;
    ImageButton toQA;

    String nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        currentlyLoggedIn = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = currentlyLoggedIn.getCurrentUser();

        toUser = findViewById(R.id.toUser);
        toNewsfeed = findViewById(R.id.toNewsfeed);
        toPeople = findViewById(R.id.toPeople);
        toStudy = findViewById(R.id.toStudy);
        toQA = findViewById(R.id.toQA);

        nowUser = getIntent().getStringExtra("current");

        toUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToUsers(v);
            }
        });
        toNewsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToNewsfeed(v);
            }
        });
        toStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToStudy();
            }
        });
        toPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToPeople(v);
            }
        });


    }


    public void takeToUsers(View view){
        Intent intent = new Intent(this,userShowPage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }
    public void takeToNewsfeed(View view){
        Intent intent = new Intent(this,noticeShowPage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    public void takeToStudy(){
        Intent intent = new Intent(this,showSemesterLIst.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    private void takeToPeople(View view) {
        Intent intent = new Intent(this,PeoplePage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

}
