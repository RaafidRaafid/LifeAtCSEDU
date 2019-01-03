package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class editUserPage extends Activity {

    String userPath;
    private final static int PICK_IMAGE_NUM = 107;

    private DatabaseReference editUserDatabaseRef;
    private StorageReference editUserStorageReference;

    EditText editUserName;
    EditText editUserBatch;
    EditText editUserRoll;
    EditText editUserPhoneNumber;
    EditText editUserBio;
    Button doneEditButton;
    Button selectImage;
    ImageView editProfilePicture;

    String dpURL;
    Uri imageURI;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_layout);

        userPath = getIntent().getStringExtra("showUser");
        editUserDatabaseRef = FirebaseDatabase.getInstance().getReference(userPath);
        editUserStorageReference = FirebaseStorage.getInstance().getReference("profilepictures");

        editUserName = findViewById(R.id.editUserName);
        editUserBatch = findViewById(R.id.editUserBatch);
        editUserRoll = findViewById(R.id.editUserRoll);
        editUserPhoneNumber = findViewById(R.id.editUserPhone);
        editUserBio = findViewById(R.id.editUserBio);
        doneEditButton = findViewById(R.id.editUserDone);
        selectImage = findViewById(R.id.editUserSelectButton);
        editProfilePicture = findViewById(R.id.editUserProfileImage);

        doneEditButton.setVisibility(View.GONE);

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

                System.out.println("$$$$$$$" + user.getDpURL());
                Glide.with(editUserPage.this)
                        .load(user.getDpURL())
                        .into(editProfilePicture);

                doneEditButton.setVisibility(View.VISIBLE);
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

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    public void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_NUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_NUM && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imageURI = data.getData();

            Glide.with(this)
                    .load(imageURI)
                    .into(editProfilePicture);
        }
    }

    public void goEditProfile(){

        final User changedUser = user;

        changedUser.setName(editUserName.getText().toString().trim());
        changedUser.setBatch(editUserBatch.getText().toString().trim());
        changedUser.setRoll(editUserRoll.getText().toString().trim());
        changedUser.setPhoneNumber(editUserPhoneNumber.getText().toString().trim());
        changedUser.setName_batch(editUserName.getText().toString().trim()+"_"+editUserBatch.getText().toString().trim());
        changedUser.setName_batch_roll(editUserName.getText().toString().trim()+"_"+editUserBatch.getText().toString().trim()+"_"+editUserRoll.getText().toString().trim());
        changedUser.setDpURL(dpURL);
        changedUser.setBio(editUserBio.getText().toString().trim());

        if(imageURI != null){
            doneEditButton.setVisibility(View.GONE);
            final StorageReference fileReference = editUserStorageReference.child(user.getEmail().replace('.','&'));

            StorageTask uploadTask = fileReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Picture Gese.");
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dpURL = uri.toString();
                                    changedUser.setDpURL(dpURL);
                                }
                            });

                            doneEditButton.setVisibility(View.VISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Picture Hoynai");
                            Toast.makeText(editUserPage.this,"Failed",Toast.LENGTH_LONG).show();
                            doneEditButton.setVisibility(View.VISIBLE);
                        }
                    });


        }
        editUserDatabaseRef.setValue(changedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(editUserPage.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });
        finish();

    }
}
