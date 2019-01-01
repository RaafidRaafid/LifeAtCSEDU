package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editUserPage extends Activity {

    String userPath;

    private DatabaseReference editUserDatabaseRef;

    EditText editUserName;
    EditText editUserBatch;
    EditText editUserRoll;
    EditText editUserPhoneNumber;
    EditText editUserBio;
    Button doneEditButton;
    Button selectImage;
    ImageView editProfilePicture;

    String dpURL;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_layout);

        userPath = getIntent().getStringExtra("showUser");
        editUserDatabaseRef = FirebaseDatabase.getInstance().getReference(userPath);

        editUserName = findViewById(R.id.editUserName);
        editUserBatch = findViewById(R.id.editUserBatch);
        editUserRoll = findViewById(R.id.editUserRoll);
        editUserPhoneNumber = findViewById(R.id.editUserPhone);
        editUserBio = findViewById(R.id.editUserBio);
        doneEditButton = findViewById(R.id.editUserDone);
        selectImage = findViewById(R.id.editUserSelectButton);
        editProfilePicture = findViewById(R.id.editUserProfileImage);

        editProfilePicture.setVisibility(View.GONE);

        editUserDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                editUserName.setText(user.getName());
                editUserBatch.setText(user.getBatch());
                editUserRoll.setText(user.getRoll());
                editUserPhoneNumber.setText(user.getPhoneNumber());
                editUserBio.setText(user.getBio());
                dpURL = user.getDpURL();

                Glide.with(editUserPage.this)
                        .load(user.getDpURL())
                        .into(editProfilePicture);

                editProfilePicture.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditProfile();
            }
        });
    }

    public void goEditProfile(){

        User changedUser;
        changedUser = user;

        changedUser.setName(editUserName.getText().toString().trim());
        changedUser.setBatch(editUserBatch.getText().toString().trim());
        changedUser.setRoll(editUserRoll.getText().toString().trim());
        changedUser.setPhoneNumber(editUserPhoneNumber.getText().toString().trim());
        changedUser.setName_batch(editUserName.getText().toString().trim()+"_"+editUserBatch.getText().toString().trim());
        changedUser.setName_batch_roll(editUserName.getText().toString().trim()+"_"+editUserBatch.getText().toString().trim()+"_"+editUserRoll.getText().toString().trim());
        changedUser.setDpURL(dpURL);
        changedUser.setBio(editUserBio.getText().toString().trim());

        editUserDatabaseRef.setValue(changedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(editUserPage.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });

        finish();

    }
}
