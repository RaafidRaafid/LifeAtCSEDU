package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerUser extends Activity {

    EditText username;
    EditText password;
    EditText fullname;
    EditText batch;
    EditText roll;
    EditText email;
    Button doneButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        username = findViewById(R.id.regiUsernameField);
        password = findViewById(R.id.regiPasswordField);
        fullname = findViewById(R.id.fullNameField);
        batch = findViewById(R.id.batchField);
        roll = findViewById(R.id.rollField);
        email = findViewById(R.id.emailField);
        doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){
        String susername = username.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        String sfullname = fullname.getText().toString().trim();
        String sbatch = batch.getText().toString().trim();
        String sroll = roll.getText().toString().trim();
        String semail = email.getText().toString().trim();

        if(TextUtils.isEmpty(susername) || TextUtils.isEmpty(spassword) || TextUtils.isEmpty(sfullname) || TextUtils.isEmpty(sbatch) || TextUtils.isEmpty(sroll) || TextUtils.isEmpty(semail)){
            Toast.makeText(this, "Please fill out all the sections",Toast.LENGTH_LONG).show();
        }else{
            String id = databaseReference.push().getKey();

            User user = new User(susername,spassword,semail,sfullname,sbatch,sroll);

            databaseReference.child(id).setValue(user);

            Toast.makeText(this,"User Added",Toast.LENGTH_LONG).show();
        }
    }

}
