package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontPage extends AppCompatActivity {

    FirebaseAuth currentlyLoggedIn;

    Button toUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        currentlyLoggedIn = FirebaseAuth.getInstance();
        FirebaseUser currentUser = currentlyLoggedIn.getCurrentUser();

        toUser = findViewById(R.id.gotoUser);
    }

    public void takeToUsers(View view){
        Intent intent = new Intent(this,userShowPage.class);
        startActivity(intent);
    }
}
