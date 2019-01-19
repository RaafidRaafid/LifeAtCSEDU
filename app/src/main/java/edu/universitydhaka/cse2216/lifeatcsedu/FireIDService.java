package edu.universitydhaka.cse2216.lifeatcsedu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireIDService extends FirebaseInstanceIdService {

    DatabaseReference userDatabase;
    FirebaseAuth userAuth;

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        userAuth = FirebaseAuth.getInstance();
        String userEmail = userAuth.getCurrentUser().getEmail();
        userDatabase = FirebaseDatabase.getInstance().getReference("users/" + userEmail.replace('.','&')).child("token");
        userDatabase.setValue(token);

        System.out.println("Amar matha" + token);
    }
}
