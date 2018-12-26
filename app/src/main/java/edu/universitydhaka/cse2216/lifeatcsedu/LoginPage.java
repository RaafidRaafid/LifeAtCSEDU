package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends Activity {

    EditText useremail;
    EditText password;
    Button loginButton;
    Button registerButton;
    TextView forgotPassword;
    private FirebaseAuth loginAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        useremail = findViewById(R.id.userNameField);
        password = findViewById(R.id.regiPasswordField);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPassword = findViewById(R.id.tvForgotPassword);

        loginAuthentication = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,resetPasswordPage.class));
            }
        });
    }

    /*public void workReset(View view){
        startActivity(new Intent(LoginPage.this,resetPasswordPage.class));
    }*/

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = loginAuthentication.getCurrentUser();
        if(currentUser != null){
            //get information of user to homepage, no idea howw.
            Intent intent = new Intent(this,FrontPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void register(View view){
        Log.d("TAG", "mojai moja");
        startActivity(new Intent(this, registerUser.class));
    }

    public void login(View view){
        Log.d("TAG","Attempting Login");

        String user = useremail.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            //Toast.makeText(this,"Please input Email Correctly",Toast.LENGTH_LONG).show();
            useremail.setError("Please input Email Correctly.");
            useremail.requestFocus();
            return;
        }

        loginAuthentication.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginPage.this,"Chinsi Bro!",Toast.LENGTH_LONG).show();

                            FirebaseUser currentUser = loginAuthentication.getCurrentUser();
                            if(currentUser.isEmailVerified()){
                                Intent intent = new Intent(LoginPage.this,FrontPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginPage.this,"Verify the Email First",Toast.LENGTH_LONG).show();
                            }

                            // pass information to homepage
                        }else{
                            Toast.makeText(LoginPage.this,"Chininai bro: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
