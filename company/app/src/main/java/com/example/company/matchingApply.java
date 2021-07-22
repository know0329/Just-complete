package com.example.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class matchingApply extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference("username");

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int SEARCH_ADDRESS_ACTIVITY2 = 9000;
    ImageButton backButton2;
    ImageButton surveyButton;
    Button homeSearchButton;
    EditText homeAddress;
    EditText hospitalAddress;
    Button hospitalSearchButton;
    CheckBox checkBox;
    EditText userName;
    TextView aaaaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_apply);


        backButton2 = findViewById(R.id.backButton2);

        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        surveyButton = findViewById(R.id.surveyButton);

        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(matchingApply.this, matchingSurvey.class);
                startActivity(intent);
            }
        });

        homeAddress = (EditText) findViewById(R.id.homeAddress);
        hospitalAddress = (EditText) findViewById(R.id.hospitalAddress);
        homeSearchButton = findViewById(R.id.homeSearchButton);
        hospitalSearchButton = findViewById(R.id.hospitalSearchButton);
        checkBox = findViewById(R.id.checkBox);
        userName = findViewById(R.id.userName);
        aaaaa = findViewById(R.id.aaaaa);

        int checkvalue = 1;
        if(checkBox != null){
            checkvalue = 1;
        }
        else
            checkvalue = 0;


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                aaaaa.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error){}
        });





        if (homeSearchButton != null) {
            homeSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(matchingApply.this, DaumWebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }


        if (hospitalSearchButton != null) {
            hospitalSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent j = new Intent(matchingApply.this, DaumWebViewActivity2.class);
                    startActivityForResult(j, SEARCH_ADDRESS_ACTIVITY2);
                }
            });
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        homeAddress.setText(data);
                    }
                }
                break;
            case SEARCH_ADDRESS_ACTIVITY2:
                if (resultCode == RESULT_OK) {
                    String data2 = intent.getExtras().getString("data2");
                    if (data2 != null) {
                        hospitalAddress.setText(data2);
                    }
                }
                break;
        }
    }








}
