package com.example.accompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.accompany.model.ReservationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReservationList_Activity extends AppCompatActivity implements ReservationList_Adapter.OnReservationListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ReservationModel> reservationArrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private TextView toolbar_txt;

    @Override
    public void OnReservationClick(int position) {
        reservationArrayList.get(position);
        Intent intent = new Intent(this, Reservation_Activity.class);
        intent.putExtra("reservation",reservationArrayList.get(position));
        startActivity(intent);

    }

    class Ascending implements Comparator<ReservationModel> {
        public int compare(ReservationModel a, ReservationModel b)
        {
            long a_time=(long) a.getReservationDate();
            long b_time=(long) b.getReservationDate();
            if (a_time>b_time) return 1;
            else if (a_time==b_time) return 0;
            else  return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);
        toolbar_txt = (TextView)findViewById(R.id.toolbar_txt);
        toolbar_txt.setText("?????? ??????");


        //?????? ????????? ?????? ?????? ?????? ??????
        String uid;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = findViewById(R.id.Reservation_List);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reservationArrayList = new ArrayList<>(); // Reservation????????? ?????? ????????? ?????????

        //.orderByChild("patient").equalTo(uid)
        database = FirebaseDatabase.getInstance();//?????????????????? ????????? ????????? ??????
        databaseReference = database.getReference("reservations");//DB????????? ??????
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //?????????????????? ????????? ???????????? ???????????? ?????????
                reservationArrayList.clear();//?????? ?????? ?????????
                ArrayList<ReservationModel> NotComReservation=new ArrayList<ReservationModel>();//?????? ?????????
                ArrayList<ReservationModel> ComReservation=new ArrayList<ReservationModel>();//?????? ??????


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//??????????????? ????????? List??????

                    ReservationModel customer = snapshot.getValue(ReservationModel.class);//??????????????? Customer????????? ???????????? ?????????
                    if(uid.equalsIgnoreCase(customer.getPatient()) || uid.equalsIgnoreCase(customer.getProtector())){
                        if(customer.getAccompanySitu().equals("????????????")) ComReservation.add(customer);//?????? ????????? ???????????? ComReservation??? ?????????
                        else NotComReservation.add(customer);//????????? ??????????????? NotComReservation
                    }
                }
                Collections.sort(NotComReservation,new Ascending());
                Collections.sort(ComReservation, new Ascending());
                reservationArrayList.addAll(NotComReservation);
                reservationArrayList.addAll(ComReservation);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //????????? ??????????????? ?????? ?????????
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new ReservationList_Adapter(reservationArrayList, this,this::OnReservationClick);
        recyclerView.setAdapter(adapter);//????????????????????? ????????? ??????
    }
}