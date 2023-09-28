package com.example.ltbakingtutorials;

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

public class LoginAdmin extends AppCompatActivity implements View.OnClickListener {
    private TextView CancelLink;
    private Button LoginButtonAdmin;
    private EditText Username_admin, Password_admin;

    private ProgressBar progressBarLogin;
    boolean passwordVisible;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_admin);
        CancelLink = (TextView) findViewById(R.id.backtoLoginPage);
        CancelLink.setOnClickListener(this);

        LoginButtonAdmin = (Button) findViewById(R.id.LoginButtonAdmin);
        LoginButtonAdmin.setOnClickListener(this);

        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLoginAdmin);

        Username_admin = (EditText) findViewById(R.id.usernameAdmin);
        Username_admin.requestFocus();
        Password_admin = (EditText) findViewById(R.id.passwordAdmin);

        Password_admin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=Password_admin.getRight()-Password_admin.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = Password_admin.getSelectionEnd();
                        if (passwordVisible){
                            //set Drawable
                            Password_admin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility_off, 0);
                            //hide password
                            Password_admin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else{
                            //set Drawable
                            Password_admin.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_security, 0, R.drawable.ic_baseline_visibility, 0);
                            //hide password
                            Password_admin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        Password_admin.setSelection(selection);
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
            case R.id.LoginButtonAdmin:
                adminLogin();
                break;
            case R.id.backtoLoginPage:
                Intent i = new Intent(LoginAdmin.this, LoginForm.class);
                startActivity(i);
                finish();
                break;
        }
    }

    private void adminLogin() {
        progressBarLogin.setVisibility(View.VISIBLE);
        String admin_uname = Username_admin.getText().toString().trim();
        String admin_password = Password_admin.getText().toString().trim();

        if (admin_uname.isEmpty()) {
            progressBarLogin.setVisibility(View.GONE);
            Username_admin.setError("Username Required!");
            Username_admin.requestFocus();
            return;
        }
        if (admin_password.isEmpty()) {
            progressBarLogin.setVisibility(View.GONE);
            Password_admin.setError("Password Required!");
            Password_admin.requestFocus();
            return;
        }

        if(admin_uname.equals("LT_admin") && admin_password.equals("password@1234")){
            Intent i = new Intent(LoginAdmin.this, adminPage.class);
            startActivity(i);
            finish();
        }
        else{
            progressBarLogin.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
            progressBarLogin.setVisibility(View.GONE);
            return;
        }
    }
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this, LoginForm.class));
        finish();
    }
}