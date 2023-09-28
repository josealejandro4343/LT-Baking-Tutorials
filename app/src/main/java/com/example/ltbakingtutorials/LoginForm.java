package com.example.ltbakingtutorials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginForm extends AppCompatActivity implements View.OnClickListener{

    private TextView SignUpLink, ForgotPass, gotoAdminLink;
    private Button LoginButton;
    private EditText Username, Password;
    private DatabaseReference reference;

    private ProgressBar progressBarLogin;
    boolean passwordVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_form);

        reference = FirebaseDatabase.getInstance().getReference("credentials");

        SignUpLink = (TextView) findViewById(R.id.SignUpLink);
        SignUpLink.setOnClickListener(this);

        gotoAdminLink = (TextView) findViewById(R.id.txtlinkAdmin);
        gotoAdminLink.setOnClickListener(this);

        LoginButton = (Button) findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(this);

        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);

        Username = (EditText) findViewById(R.id.username);
        Username.requestFocus();
        Password = (EditText) findViewById(R.id.password);

        Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=Password.getRight()-Password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = Password.getSelectionEnd();
                        if (passwordVisible){
                            //set Drawable
                            Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility_off, 0);
                            //hide password
                            Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else{
                            //set Drawable
                            Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility, 0);
                            //hide password
                            Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        Password.setSelection(selection);
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
            case R.id.SignUpLink:
                startActivity(new Intent(this, RegisterForm.class));
                break;
            case R.id.LoginButton:
                userLogin();
                break;

            case R.id.txtlinkAdmin:
                startActivity(new Intent(this, LoginAdmin.class));
                finish();
                break;
        }
    }

    private void userLogin() {
        String user_uname = Username.getText().toString().toLowerCase().trim();
        String user_password = Password.getText().toString().toLowerCase().trim();

        if (user_uname.isEmpty()) {
            Username.setError("Username Required!");
            Username.requestFocus();
            return;
        }
        if (user_password.isEmpty()) {
            Password.setError("Password Required!");
            Password.requestFocus();
            return;
        }

        reference.child(user_uname)
                .addListenerForSingleValueEvent(valueEventListener);
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String user_uname = Username.getText().toString().toLowerCase().trim();
            String user_password2 = Password.getText().toString().toLowerCase().trim();
            if(snapshot.exists()){
                String password = snapshot.child("pass")
                        .getValue(String.class);
                String user_password = Password.getText().toString().toLowerCase().trim();
                if(password.equals(user_password)){
                    Intent i = new Intent(LoginForm.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(LoginForm.this, "Login Failed, Wrong Password!", Toast.LENGTH_LONG).show();
                    progressBarLogin.setVisibility(View.GONE);
                }
            }
            else{
                Toast.makeText(LoginForm.this, "Record not Found!", Toast.LENGTH_LONG).show();
                progressBarLogin.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(LoginForm.this,  error.toString(), Toast.LENGTH_SHORT).show();
        }
    };

}