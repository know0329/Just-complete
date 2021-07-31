package com.example.accompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.accompany.model.ReservationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ReservationList_Adapter extends RecyclerView.Adapter<ReservationList_Adapter.ReservationViewholder>  {

    private static final int TYPE_DEFAULT = 1;
    private static final int TYPE_ING = 2;
    private static final int TYPE_ACCOMPLISH = 3;

    private ArrayList<ReservationModel> reservationArrayList;
    private Context context;
    //날짜 데이터 형식
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private  OnReservationListener mOnReservationListener;



    public ReservationList_Adapter(ArrayList<ReservationModel> customerArrayList, Context context, OnReservationListener onReservationListener)
    {
        this.reservationArrayList = customerArrayList;
        this.context=context;
        this.mOnReservationListener = onReservationListener;
    }

    //어떤 카드뷰 형태로 할것인지 결정
    public int getItemViewType(int position) {
        ReservationModel reservation = reservationArrayList.get(position);
        if (reservation.getAccompanySitu().equals("동행완료")) {
            return TYPE_ACCOMPLISH;
        } else if (reservation.getAccompanySitu().equals("동행중")) {
            return TYPE_ING;
        } else if (reservation.getAccompanySitu().equals("동행전")) {
            return TYPE_DEFAULT;
        } else {
            return -1;
        }
    }
    @NonNull
    @Override
    //뷰홀더 전달
    public ReservationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_layout,parent,false);
        //ReservationViewholder holder = new ReservationViewholder(view);
        if(viewType == TYPE_ACCOMPLISH){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_layout_accomplished,parent,false);
            return new ReservationViewholder(view, mOnReservationListener);
        }else if(viewType == TYPE_DEFAULT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_layout,parent,false);
            return new ReservationViewholder(view, mOnReservationListener);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_layout_ing,parent,false);
            return new ReservationViewholder(view, mOnReservationListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ReservationList_Adapter.ReservationViewholder holder, int position) {
        //holder.tv_reservationDate.setText(customerArrayList.get(position).getReservationDate());
        holder.tv_hospital.setText(reservationArrayList.get(position).getHospital());
        holder.tv_accompanySitu.setText(reservationArrayList.get(position).getAccompanySitu());

        //날짜 데이터
        long unixTime = (long) reservationArrayList.get(position).getReservationDate();
        Date date=new Date(unixTime);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String time = simpleDateFormat.format(date);
        holder.tv_reservationDate.setText(time);

    }

    @Override
    public int getItemCount() {
        return (reservationArrayList != null ? reservationArrayList.size() : 0);
    }

    public class ReservationViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_reservationDate;
        TextView tv_hospital;
        TextView tv_accompanySitu;

        OnReservationListener onReservationListener;

        public ReservationViewholder(@NonNull View itemView, OnReservationListener onReservationListener){
            super(itemView);
            this.tv_accompanySitu=itemView.findViewById(R.id.tv_accompanySitu);
            this.tv_hospital=itemView.findViewById(R.id.tv_hospital);
            this.tv_reservationDate=itemView.findViewById(R.id.tv_reservationDate);

            this.onReservationListener = onReservationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onReservationListener.OnReservationClick(getAdapterPosition());
        }
    }
    public interface OnReservationListener{
        void OnReservationClick(int position);
    }

}