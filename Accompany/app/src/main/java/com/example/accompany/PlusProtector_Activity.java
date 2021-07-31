package com.example.accompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accompany.model.ReservationModel;
import com.example.accompany.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PlusProtector_Activity extends AppCompatActivity {

    private TextView toolbar_txt;
    private ReservationModel reservation;

    private EditText id;
    private ImageView find;

    private  TextView name;
    private  ImageView userImage;
    private Button plus;
    private CardView findcard;
    private TextView notfound;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private UserModel user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_protector);

        //toolbar 글자
        toolbar_txt = (TextView)findViewById(R.id.toolbar_txt);
        toolbar_txt.setText("보호자/환자 추가");

        //intent로 해당 예약 정보 불러오기
        reservation = getIntent().getParcelableExtra("reservation");

        //id 가져오기
        id = (EditText)findViewById(R.id.plus_protector_text_id);
        find = (ImageView) findViewById(R.id.plus_protector_image_find);
        name = (TextView)findViewById(R.id.plus_protector_text_name);
        userImage = (ImageView)findViewById(R.id.plus_protector_image_user);
        plus = (Button)findViewById(R.id.plus_protector_button_plus);
        findcard = (CardView)findViewById(R.id.plus_protector_card_user);
        notfound = (TextView)findViewById(R.id.plus_protector_text_notfound);



        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findcard.setVisibility(View.INVISIBLE);
                databaseReference = FirebaseDatabase.getInstance().getReference("users");//파이어베이스 데이터 베이스 연동
                databaseReference.orderByChild("userName").equalTo(id.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getChildrenCount() >0){
                            for(DataSnapshot userSnapshot : snapshot.getChildren()){
                                user = userSnapshot.getValue(UserModel.class);
                                name.setText(user.getUserName());
                                findcard.setVisibility(View.VISIBLE);
                            }
                        }else {
                            notfound.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        //디비를 가져오던중 에러 발생시
                        Log.e("MainActivity", String.valueOf(error.toException()));
                    }


                });

            }

        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();
                reservation.protector = uid;
                DatabaseReference reservationRef = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> reservationUpdates = new HashMap<>();

                reservationUpdates.put("/reservations/"+reservation.getReservationId()+"/protector", uid);
                reservationRef.updateChildren(reservationUpdates);
            }
        });

    }
}