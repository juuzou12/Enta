package com.example.enta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enta.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinalActivity extends AppCompatActivity {

    /*LAYOUTS*/
    ConstraintLayout music_layout,movie_layout,tickets_layout,radio_layout,username_layout;
    /*TEXTVIES*/
    TextView radio_back,ticket_next,ticket_back,movie_back,movie_next,music_next,done,username_back;
    /*BUTTON*/
    Button done2;
    /*EDITTEXT*/
    EditText username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*FULLSCREEN*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_final);
        /*NAVIGATION*/
        navigation();
        /*DATA INTO THE DATABASE*/
        done2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               /* if (userFb==null){
                    Toast.makeText(FinalActivity.this, "Kindly login to Enta", Toast.LENGTH_SHORT).show();
                    Intent fp=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(fp);
                }else {
                    dataToDatabase();
                }
*/
               dataToDatabase();
            }
        });

    }



    /*NAVIGATION*/
    private void navigation() {
        /*TextView*/
        music_next=findViewById(R.id.music_next);
        movie_next=findViewById(R.id.movie_next);
        movie_back=findViewById(R.id.movie_back);
        ticket_back=findViewById(R.id.ticket_back);
        ticket_next=findViewById(R.id.ticket_next);
        radio_back=findViewById(R.id.radio_back);
        username_back=findViewById(R.id.username_back);

        /*LAYOUT*/
        music_layout=findViewById(R.id.music_layout);
        movie_layout=findViewById(R.id.movie_layout);
        tickets_layout=findViewById(R.id.tickets_layout);
        radio_layout=findViewById(R.id.radio_layout);
        username_layout=findViewById(R.id.username_layout);

        /*BUTTON*/
        done=findViewById(R.id.done);
        done2=findViewById(R.id.done2);

        /*ONCLICKS*/
        /*TODO...MUSIC*/
        music_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_layout.setVisibility(View.GONE);
                movie_layout.setVisibility(View.VISIBLE);
            }
        });

        /*TODO...MOVIES*/
        movie_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickets_layout.setVisibility(View.VISIBLE);
                movie_layout.setVisibility(View.GONE);
            }
        });
        movie_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_layout.setVisibility(View.VISIBLE);
                movie_layout.setVisibility(View.GONE);
            }
        });

        /*TODO...tickets*/
        ticket_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickets_layout.setVisibility(View.GONE);
                movie_layout.setVisibility(View.VISIBLE);
            }
        });
        ticket_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tickets_layout.setVisibility(View.GONE);
                radio_layout.setVisibility(View.VISIBLE);
            }
        });

        /*TODO...tickets*/
        radio_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_layout.setVisibility(View.GONE);
                tickets_layout.setVisibility(View.VISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_layout.setVisibility(View.GONE);
                username_layout.setVisibility(View.VISIBLE);
            }
        });
        username_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_layout.setVisibility(View.VISIBLE);
                username_layout.setVisibility(View.GONE);
            }
        });
    }

    /*DATA INTO THE DATABASE*/
    public void dataToDatabase(){
        //DATABASE WITH FIREBASE CODEs.......
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference;
        databaseReference = database.getReference("UsersInfo").child("Users_info");
        username=findViewById(R.id.username);
        final String Username = username.getText().toString().trim();

            if ((Username.isEmpty())){
                Toast.makeText(this, "Fill all field", Toast.LENGTH_SHORT).show();
            }else {
                String id = databaseReference.push().getKey();
                UserModel userModel=new UserModel(Username);
                userModel.setUsername(username.getText().toString().trim());
                databaseReference.child(id).setValue(userModel);
            }
        }

    }

