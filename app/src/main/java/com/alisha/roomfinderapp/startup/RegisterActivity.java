package com.alisha.roomfinderapp.startup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alisha.roomfinderapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class  RegisterActivity extends AppCompatActivity {

    EditText emailField, nameField, passField, cPassField;
    private ImageView createAccount;
    String email, name, pass, cpass;
    private FirebaseAuth mAuth;
    private static final String TAG = "CreateActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            FirebaseAuth.getInstance().signOut();
        } else {
            // No user is signed in
        }
        mAuth = FirebaseAuth.getInstance();

        setupUIViews();

        createAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                email = emailField.getText().toString();
                name = nameField.getText().toString();
                pass  = passField.getText().toString();
                cpass = cPassField.getText().toString();

                if(validateEmail(email) && validatePassword(pass) && validatePassword(cpass)) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, "Authentication successful.",
                                                Toast.LENGTH_LONG).show();


                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        FirebaseUser current_user = auth.getCurrentUser();

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .build();

                                        current_user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");

                                                        }
                                                    }
                                                });

                                        current_user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent.");
                                                        }
                                                    }
                                                });
                                        Intent signinIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        signinIntent.putExtra("email", email);
                                        signinIntent.putExtra("password",pass);
                                        RegisterActivity.this.startActivity(signinIntent);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else if(!pass.equals(cpass)){
                    Toast.makeText(getApplicationContext(),"Passwords Don't Match", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter Valid Email and Password", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void setupUIViews() {
        emailField = (EditText) findViewById(R.id.create_acc_email_text);
        nameField = (EditText) findViewById(R.id.create_acc_username_text);
        passField = (EditText) findViewById(R.id.create_acc_pwd_text);
        cPassField = (EditText) findViewById(R.id.create_acc_conform_pwd_text);
        createAccount= findViewById(R.id.create_acc_arrow);
    }



    public boolean validateEmail(String email){
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
    public boolean validatePassword(String password){
        if(password.length() < 6 )
            return false;
        else
            return true;
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}


