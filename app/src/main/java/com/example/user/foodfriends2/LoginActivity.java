package com.example.user.foodfriends2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile activiry here
            finish();
            startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
        }


        editTextEmail = (EditText) findViewById(R.id.editTextEMail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonRegister);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUP);


        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();
            // password is empty
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();

                       if(task.isSuccessful()){
                           //startpofile activity
                           finish();
                           startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
                   }
               }

                });





    }
    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin();
        }
        if(view == textViewSignUp){
            finish();
            startActivity(new Intent(this, Login.class));
        }

    }
}
