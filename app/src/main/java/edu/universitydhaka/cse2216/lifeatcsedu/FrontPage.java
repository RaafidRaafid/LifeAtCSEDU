package edu.universitydhaka.cse2216.lifeatcsedu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontPage extends AppCompatActivity {

    FirebaseAuth currentlyLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        currentlyLoggedIn = FirebaseAuth.getInstance();
        FirebaseUser currentUser = currentlyLoggedIn.getCurrentUser();

        System.out.println("dekh dekh dekh dkhe dekh " + currentUser.getEmail());
    }
}
