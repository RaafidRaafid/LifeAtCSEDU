package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerUser extends Activity {

    EditText password;
    EditText fullname;
    EditText batch;
    EditText roll;
    EditText email;
    Button doneButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth userAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userAuthentication = FirebaseAuth.getInstance();

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
        String spassword = password.getText().toString().trim();
        String sfullname = fullname.getText().toString().trim();
        String sbatch = batch.getText().toString().trim();
        String sroll = roll.getText().toString().trim();
        String semail = email.getText().toString().trim();

        if(TextUtils.isEmpty(spassword) || TextUtils.isEmpty(sfullname) || TextUtils.isEmpty(sbatch) || TextUtils.isEmpty(sroll) || TextUtils.isEmpty(semail)){
            Toast.makeText(this, "Please fill out all the sections",Toast.LENGTH_LONG).show();
        }else{
            String id = databaseReference.push().getKey();

            User user = new User(spassword,semail,sfullname,sbatch,sroll);

            databaseReference.child(id).setValue(user);

            userAuthentication.createUserWithEmailAndPassword(semail,spassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // Carry information of the user to the homepage or some thing :/

                            }else{
                                Toast.makeText(registerUser.this,"Hoynai",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            Toast.makeText(this,"User Added",Toast.LENGTH_LONG).show();
        }
    }

}
