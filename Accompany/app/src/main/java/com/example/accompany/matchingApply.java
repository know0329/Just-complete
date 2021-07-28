package com.example.accompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class matchingApply extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int SEARCH_ADDRESS_ACTIVITY2 = 9000;
    ImageButton backButton2;
    ImageButton surveyButton;
    Button homeSearchButton;
    EditText homeAddress;
    EditText hospitalAddress;
    EditText birthday;
    EditText phonenumber;
    EditText dateView;
    Button hospitalSearchButton;
    Button DateButton;
    CheckBox checkBox;
    EditText userName;
    String userid ="1";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_apply);


        backButton2 = findViewById(R.id.backButton2);
        /*뒤로 가기 버튼 */
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        surveyButton = findViewById(R.id.surveyButton);
        /*설문 조사 버튼 */
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
        birthday = findViewById(R.id.birthday);
        phonenumber = findViewById(R.id.phonenumber);
        dateView = findViewById(R.id.dateView);


        /*사전 정보 입력 체크 박스 */
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    /*데이터베이스의 회원정보 가져오기 */
                    mDatabase.child("users").child(userid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI
                            User post = dataSnapshot.getValue(User.class);
                            userName.setText(post.getUserName());
                            homeAddress.setText(post.getHomeaddress());
                            phonenumber.setText(post.getPhonenumber());
                            birthday.setText(post.getBirthday());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message

                        }
                    });



                }
                else {
                    userName.setText("");
                    homeAddress.setText("");
                    phonenumber.setText("");
                    birthday.setText("");
                }
            }
        });


        /*집 주소 찾기 */
        if (homeSearchButton != null) {
            homeSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(matchingApply.this, DaumWebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

        /*병원 찾기 */
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
                        mDatabase.child("users").child(userid).child("hospitalAddress").setValue(data2);
                    }
                }
                break;
        }
    }
    /*날짜 검색 */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        dateView.setText(dateMessage);
    }
}