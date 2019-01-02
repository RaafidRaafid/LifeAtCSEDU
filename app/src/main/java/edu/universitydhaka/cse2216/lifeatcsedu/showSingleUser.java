package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSingleUser extends Activity {

    private FirebaseUser currentUser;
    private FirebaseAuth showSingleUserAuth;
    private DatabaseReference showSingleUserDatabaseRef;

    TextView name_value;
    TextView email_value;
    TextView batch_value;
    TextView roll_value;
    TextView phoneNumber_value;
    TextView bio_value;
    ImageView profilePicture;
    Button edit_profilePhoto_button;
    Button toUserQuestion;
    Button toUserAnswers;

    String  userEmail,nowUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_layout);

        name_value = findViewById(R.id.name_value);
        email_value = findViewById(R.id.email_value);
        batch_value = findViewById(R.id.batch_value);
        roll_value = findViewById(R.id.roll_value);
        phoneNumber_value = findViewById(R.id.phone_value);
        bio_value = findViewById(R.id.bio_value);
        profilePicture = findViewById(R.id.singleUser_profile_image);
        edit_profilePhoto_button = findViewById(R.id.Edit_photo_button);
        toUserAnswers = findViewById(R.id.toUserAnswers);
        toUserQuestion = findViewById(R.id.toUserQuestions);

        showSingleUserAuth = FirebaseAuth.getInstance();
        currentUser = showSingleUserAuth.getCurrentUser();
        userEmail = currentUser.getEmail();
        nowUser = getIntent().getStringExtra("showUser");
        System.out.println("users/"+nowUser.replace('.','&'));
        showSingleUserDatabaseRef = FirebaseDatabase.getInstance().getReference("users/" +nowUser.replace('.','&'));

        showSingleUserDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                name_value.setText(user.getName());
                email_value.setText(user.getEmail());
                batch_value.setText(user.getBatch());
                roll_value.setText(user.getRoll());
                phoneNumber_value.setText(user.getPhoneNumber());
                bio_value.setText(user.getBio());
                System.out.println("@@@@@@@@@" + user.getDpURL());
                Glide.with(showSingleUser.this)
                        .load(user.getDpURL())
                        .into(profilePicture);

                if(userEmail.equals(user.getEmail())){
                    edit_profilePhoto_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit_profilePhoto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEditProfilePicture();
            }
        });
    }

    public void toEditProfilePicture(){
        Intent intent = new Intent(this,editUserPage.class);
        intent.putExtra("showUser","users/"+nowUser.replace('.','&'));
        startActivity(intent);
    }

    public void gotoQuestions(View view){

    }

    public void gotoAnswers(View view){

    }
}
