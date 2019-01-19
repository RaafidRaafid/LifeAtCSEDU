package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

public class registerUser extends Activity {

    EditText password;
    EditText fullname;
    EditText batch;
    EditText roll;
    EditText email;
    EditText phoneNumber;
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
        phoneNumber = findViewById(R.id.phoneNumberField);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){
        final FirebaseUser hmmuser = userAuthentication.getCurrentUser();
        //System.out.println("asd sfg sfdg sdfg sd " + hmmuser.getEmail());

        final String spassword = password.getText().toString().trim();
        final String sfullname = fullname.getText().toString().trim();
        final String sbatch = batch.getText().toString().trim();
        final String sroll = roll.getText().toString().trim();
        final String semail = email.getText().toString().trim();
        final String sphoneNumber;

        if(TextUtils.isEmpty(phoneNumber.getText().toString().trim())) sphoneNumber = "null";
        else sphoneNumber = phoneNumber.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            //Toast.makeText(this,"Please input Email Correctly",Toast.LENGTH_LONG).show();
            email.setError("Please input Email Correctly.");
            email.requestFocus();
            return;
        }
        if(spassword.length() < 8){
            //Toast.makeText(this,"Password has to be at least 8 chars",Toast.LENGTH_LONG).show();
            password.setError("Password has to be at least 8 chars");
            password.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(spassword) || TextUtils.isEmpty(sfullname) || TextUtils.isEmpty(sbatch) || TextUtils.isEmpty(sroll) || TextUtils.isEmpty(semail)){
            Toast.makeText(this, "Please fill out all the sections",Toast.LENGTH_LONG).show();
        }else{
            System.out.println("@@@@@@@@@@@" + semail + " " + spassword);
            userAuthentication.createUserWithEmailAndPassword(semail,spassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(semail, sfullname, sbatch,sroll,sphoneNumber, sfullname + "_" + sbatch, sfullname + "_" + sbatch + "_" + sroll,"https://i.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg","false","Nothing yet.","-1");
                                databaseReference.child(semail.replace('.','&')).setValue(user);
                                FirebaseUser hmmttuser = userAuthentication.getCurrentUser();
                                hmmttuser.sendEmailVerification();


                                Toast.makeText(registerUser.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                                email.setText("");
                                password.setText("");
                                phoneNumber.setText("");
                                fullname.setText("");
                                roll.setText("");
                                batch.setText("");

                                Intent intent = new Intent(registerUser.this, LoginPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                userAuthentication.signOut();
                                startActivity(intent);
                            }else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(registerUser.this, "Email alredy taken.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(registerUser.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }

}
