package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private FirebaseAuth loginAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        useremail = findViewById(R.id.userNameField);
        password = findViewById(R.id.regiPasswordField);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginAuthentication = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = loginAuthentication.getCurrentUser();
        if(currentUser != null){
            //get information of user to homepage, no idea how.
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

        loginAuthentication.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser = loginAuthentication.getCurrentUser();
                            Toast.makeText(LoginPage.this,"Chinsi Bro!",Toast.LENGTH_LONG).show();
                            // pass information to homepage
                        }else{
                            Toast.makeText(LoginPage.this,"Chininai bro",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
