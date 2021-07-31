package com.example.accompany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton MatchingApplyButton;
    ImageButton testButton;
    private CardView cdv_Reservation_list;
    private CardView cdv_Apply_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cdv_Apply_list = findViewById(R.id.cdv_Apply_list);

        cdv_Apply_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,matchingApply.class);
                startActivity(intent);
            }
        });
        cdv_Reservation_list = findViewById(R.id.cdv_Reservation_list);
        cdv_Reservation_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReservationList_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }

}