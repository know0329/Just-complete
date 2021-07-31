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
        toolbar_txt.setText("예약 상세");


        //현재 유저가 속한 방만 얻기 위해
        String uid;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = findViewById(R.id.Reservation_List);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reservationArrayList = new ArrayList<>(); // Reservation객체를 담을 어레이 리스트

        //.orderByChild("patient").equalTo(uid)
        database = FirebaseDatabase.getInstance();//파이어베이스 데이터 베이스 연동
        databaseReference = database.getReference("reservations");//DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //파이어베이스 데이터 베이스의 데이터를 받아옴
                reservationArrayList.clear();//기존 배열 초기화
                ArrayList<ReservationModel> NotComReservation=new ArrayList<ReservationModel>();//동행 안끝난
                ArrayList<ReservationModel> ComReservation=new ArrayList<ReservationModel>();//동행 끝난


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 List추출

                    ReservationModel customer = snapshot.getValue(ReservationModel.class);//만들어뒀던 Customer객체에 데이터를 담는다
                    if(uid.equalsIgnoreCase(customer.getPatient()) || uid.equalsIgnoreCase(customer.getProtector())){
                        if(customer.getAccompanySitu().equals("동행완료")) ComReservation.add(customer);//만약 동행이 끝났다면 ComReservation에 더하고
                        else NotComReservation.add(customer);//동행이 안끝났으면 NotComReservation
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
                //디비를 가져오던중 에러 발생시
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        adapter = new ReservationList_Adapter(reservationArrayList, this,this::OnReservationClick);
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
    }
}