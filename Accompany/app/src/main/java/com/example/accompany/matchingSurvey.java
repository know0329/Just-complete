package com.example.accompany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class matchingSurvey extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_survey);

        RadioGroup GenderGroup;


        RadioGroup CarGroup;


        RadioGroup ComeGroup;


        RadioGroup WheelGroup;

        ImageButton matching;

        GenderGroup = findViewById(R.id.GenderGroup);
        String userid = "1";
        GenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.Men){
                    mDatabase.child("users").child(userid).child("survey").child("Gender").setValue("M");
                }
                else if(checkedId == R.id.Women){

                    mDatabase.child("users").child(userid).child("survey").child("Gender").setValue("W");
                }
                else if(checkedId == R.id.Nomatter){

                    mDatabase.child("users").child(userid).child("survey").child("Gender").setValue("N");
                }

            }
        });


        CarGroup = findViewById(R.id.CarGroup);
        CarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.HaveCar){
                    mDatabase.child("users").child(userid).child("survey").child("Car").setValue("Have");
                }
                else if(checkedId == R.id.Nocar){

                    mDatabase.child("users").child(userid).child("survey").child("Car").setValue("Not");
                }

            }
        });

        ComeGroup = findViewById(R.id.ComeGroup);
        ComeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.ComeYes){
                    mDatabase.child("users").child(userid).child("survey").child("Come").setValue("Yes");
                }
                else if(checkedId == R.id.ComeNo){

                    mDatabase.child("users").child(userid).child("survey").child("Come").setValue("No");
                }

            }
        });

        WheelGroup = findViewById(R.id.WheelChairGroup);
        WheelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.HaveWheel){
                    mDatabase.child("users").child(userid).child("survey").child("WheelChair").setValue("Have");
                }
                else if(checkedId == R.id.NoWheel){

                    mDatabase.child("users").child(userid).child("survey").child("WheelChair").setValue("Not");
                }

            }
        });



    }
}