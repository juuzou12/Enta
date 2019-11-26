package com.example.enta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    /*BUTTON*/
    Button login_btn,signup_btn;

    /*TEXTVIEW*/
    TextView ihaveacc, createacc;

    /*CARDVIEWS*/
    CardView signup_layout, login_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*FULLSCREEN*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        navigaion();
        /*LOGGING */
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        /*SIGN UP*/
        addNewUser();


    }

    public void navigaion() {
        /*ANIAMTION*/
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_to_left);
        final Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.slide_to_right);

        createacc = findViewById(R.id.createacc);
        ihaveacc = findViewById(R.id.ihaveacc);

        signup_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);

        login_layout = findViewById(R.id.login_layout);
        signup_layout = findViewById(R.id.signup_layout);

        /*ONCLICK*/
        ihaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login_layout.setVisibility(View.VISIBLE);
                login_btn.setVisibility(View.VISIBLE);

                signup_layout.setVisibility(View.GONE);
                signup_btn.setVisibility(View.GONE);

                signup_layout.startAnimation(anim2);
                signup_btn.startAnimation(anim2);

                login_layout.startAnimation(anim);
                login_btn.startAnimation(anim);

            }
        });

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_layout.setVisibility(View.VISIBLE);
                signup_btn.setVisibility(View.VISIBLE);

                signup_layout.startAnimation(anim);
                signup_btn.startAnimation(anim);

                login_layout.setVisibility(View.GONE);
                login_layout.startAnimation(anim2);
                login_btn.startAnimation(anim2);

            }
        });
    }

    /*Login users*/
    private void LoginUser() {
        /*EDITTEXT USED*/
        final EditText reg_email, reg_password;
        final ProgressBar progressBar;
        final Button login;
        final FirebaseAuth auth1;


        FirebaseAuth.AuthStateListener mAuthListener;

        /*Giving them ids*/
        reg_email =findViewById(R.id.emailman);
        reg_password =findViewById(R.id.password2);


        //Get Firebase auth instance
        auth1 = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userFb = auth1.getCurrentUser();
                if (userFb == null) {
                    signup_layout.setVisibility(View.VISIBLE);
                    login_layout.setVisibility(View.GONE);
                    return;
                }
            }
        };
        String Email = reg_email.getText().toString().trim();
        String Password = reg_password.getText().toString().trim();

        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || Password.length() < 6) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            auth1.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent fp=new Intent(getApplicationContext(),FinalActivity.class);
                                startActivity(fp);
                            }else {
                                Toast.makeText(MainActivity.this, "Login Failed.Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }
    /*Sign Up a new user*/
    private void addNewUser() {
        final EditText reg_email, reg_password;
        final FirebaseAuth.AuthStateListener mAuthListener;

        reg_email = findViewById(R.id.emailman2);
        reg_password =findViewById(R.id.password);


        /*ClickListener*/
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = reg_email.getText().toString().trim();
                String password = reg_password.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    final FirebaseAuth auth;
                    //Get Firebase auth instance
                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent fp=new Intent(getApplicationContext(),FinalActivity.class);
                                    startActivity(fp);
                                }
                            });
                }
            }
        });
    }
}

