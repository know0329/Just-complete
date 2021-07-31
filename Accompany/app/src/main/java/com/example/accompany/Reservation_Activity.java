package com.example.accompany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accompany.model.ChatModel;
import com.example.accompany.model.ReservationModel;
import com.google.firebase.auth.FirebaseAuth;

public class Reservation_Activity extends AppCompatActivity {

    private TextView name;
    private TextView age;
    private TextView car;
    private CardView chat;

    private Button gps;
    private Button protector;
    private String accompany;
    private ReservationModel reservationModel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        //intent 받아오기
        if(getIntent().hasExtra("reservation")){
            reservationModel = getIntent().getParcelableExtra("reservation");
        }

        name = (TextView)findViewById(R.id.reservation_text_name);
        age = (TextView)findViewById(R.id.reservation_text_age);
        car = (TextView)findViewById(R.id.reservation_text_car);
        chat = (CardView)findViewById(R.id.reservation_cardview_chat);

        gps = (Button)findViewById(R.id.reservation_button_gps);
        protector = (Button)findViewById(R.id.reservation_button_protector);
        //예시name.setText(reservationModel.getHospital());
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chatModel = new ChatModel();
                chatModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                chatModel.accompany = accompany;

                //FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel);
            }
        });

        protector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation_Activity.this,PlusProtector_Activity.class);
                intent.putExtra("reservation",reservationModel);
                startActivity(intent);
            }
        });


    }
}