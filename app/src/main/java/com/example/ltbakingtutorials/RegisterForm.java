package com.example.ltbakingtutorials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterForm extends AppCompatActivity implements View.OnClickListener {
    private Button Register, ClearAllFields;
    private TextView BacktoLogin;
    private EditText user_name, user_age, user_email, user_uname, user_pass1;
    private ProgressBar progressBarSignUp;
    boolean passwordVisible;
    private DatabaseReference reference;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_form);
        reference = FirebaseDatabase.getInstance().getReference("credentials");

        Register = (Button) findViewById(R.id.RegisterButton);
        Register.setOnClickListener(this);

        BacktoLogin = (TextView) findViewById(R.id.BacktoLogin);
        BacktoLogin.setOnClickListener(this);

        ClearAllFields = (Button) findViewById(R.id.clearAllFields);
        ClearAllFields.setOnClickListener(this);

        progressBarSignUp = (ProgressBar) findViewById(R.id.progressBarRegister);

        user_name = (EditText) findViewById(R.id.user_name);
        user_age = (EditText) findViewById(R.id.age);
        user_age.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "99" )}) ;

        user_email = (EditText) findViewById(R.id.email);
        user_uname = (EditText) findViewById(R.id.username);
        user_pass1 = (EditText) findViewById(R.id.user_password);

        user_pass1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=user_pass1.getRight()-user_pass1.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = user_pass1.getSelectionEnd();
                        if (passwordVisible){
                            //set Drawable
                            user_pass1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility_off, 0);
                            //hide password
                            user_pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else{
                            //set Drawable
                            user_pass1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility, 0);
                            //hide password
                            user_pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        user_pass1.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BacktoLogin:
                startActivity(new Intent(this, LoginForm.class));
                finish();
                break;
            case R.id.RegisterButton:
                registerUser();
                break;
            case R.id.clearAllFields:
                clearAllFields();
                break;
        }
    }

    private void clearAllFields() {
        user_name.getText().clear();
        user_age.getText().clear();
        user_email.getText().clear();
        user_uname.getText().clear();
        user_pass1.getText().clear();
    }

    private void registerUser() {
        String name = user_name.getText().toString().trim();
        String age = user_age.getText().toString().trim();
        int u_age;
        u_age = Integer.parseInt(user_age.getText().toString());
        String email = user_email.getText().toString().trim();
        String username = user_uname.getText().toString().trim().toLowerCase();
        String pass1 = user_pass1.getText().toString().trim();

        if (name.isEmpty()) {
            user_name.setError("Name Required!");
            user_name.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            user_age.setError("Age Required!");
            user_age.requestFocus();
            return;
        }
        if (u_age < 18) {
            user_age.setError("Age should be 18 and above!");
            user_age.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            user_email.setError("Email Address Required!");
            user_email.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            user_uname.setError("Username Required!");
            user_uname.requestFocus();
            return;
        }
        if (pass1.isEmpty()) {
            user_pass1.setError("Password Required!");
            user_pass1.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user_email.setError("Please use a valid email address.");
            user_email.requestFocus();
            return;
        }
        if (pass1.length() < 6) {
            user_pass1.setError("Minimum password length should be 6 characters!");
            user_pass1.requestFocus();
            return;
        }

        progressBarSignUp.setVisibility(View.VISIBLE);
        Users user = new Users(name, age, email, username, pass1);
        reference.child(username).setValue(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterForm.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBarSignUp.setVisibility(View.GONE);
                        clearAllFields();
                        Toast.makeText(RegisterForm.this, "Account Registered Successfully!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}