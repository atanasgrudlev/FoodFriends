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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile activiry here
            finish();
            startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
        }



        progressDialog=new ProgressDialog(this);


        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmail= (EditText)findViewById(R.id.editTextEMail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword) ;
        textViewSignin=(TextView) findViewById(R.id.textViewSignIn);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            // password is empty
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
                            } else {


                                Toast.makeText(Login.this, "Could not Register please try again", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();


                        }



                });
    }
    @Override
    public void onClick(View view){
            if (view == buttonRegister) {
                registerUser();
            }
            if (view == textViewSignin) {
                startActivity(new Intent(this, LoginActivity.class));

            }
        }

}
