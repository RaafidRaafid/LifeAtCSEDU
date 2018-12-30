package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontPage extends AppCompatActivity {

    FirebaseAuth currentlyLoggedIn;

    ImageButton toUser;
    ImageButton toNewsfeed;
    ImageButton toPeople;
    ImageButton toStudy;
    ImageButton toQA;

    User nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        currentlyLoggedIn = FirebaseAuth.getInstance();
        FirebaseUser currentUser = currentlyLoggedIn.getCurrentUser();

        toUser = findViewById(R.id.toUser);
        toNewsfeed = findViewById(R.id.toNewsfeed);
        toPeople = findViewById(R.id.toPeople);
        toStudy = findViewById(R.id.toStudy);
        toQA = findViewById(R.id.toQA);

        nowUser = (User) getIntent().getParcelableExtra("current");
        //System.out.println("ESHE GECHI FRONT E");
        System.out.println(nowUser.getName_batch_roll() + "############");

        toUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToUsers(v);
            }
        });
    }

    public void takeToUsers(View view){
        Intent intent = new Intent(this,userShowPage.class);
        startActivity(intent);
    }
}
