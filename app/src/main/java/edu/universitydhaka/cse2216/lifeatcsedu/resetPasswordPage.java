package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPasswordPage extends Activity {

    EditText resetEmail;
    Button resetSend;

    FirebaseAuth resetPasswordAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        resetEmail = findViewById(R.id.resetPasswordEmail);
        resetSend = findViewById(R.id.resetPasswordButton);

        resetPasswordAuth = FirebaseAuth.getInstance();
    }

    public void reset(View view){
        String email = resetEmail.getText().toString();

        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Input the Email Properly, mate.",Toast.LENGTH_LONG).show();
        }else{
            resetPasswordAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(resetPasswordPage.this,"Done. Check your Email.",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(resetPasswordPage.this,LoginPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(resetPasswordPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
