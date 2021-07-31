package com.example.accompany.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReservationModel implements Parcelable {

    public String protector;
    public String patient;
    public String accompany;
    public String accompanySitu;
    public String hospital;
    public Object reservationDate;
    public String reservationId;

    public ReservationModel(){}

    protected ReservationModel(Parcel in) {
        protector = in.readString();
        patient = in.readString();
        accompany = in.readString();
        accompanySitu = in.readString();
        hospital = in.readString();
        reservationId = in.readString();
    }

    public static final Creator<ReservationModel> CREATOR = new Creator<ReservationModel>() {
        @Override
        public ReservationModel createFromParcel(Parcel in) {
            return new ReservationModel(in);
        }

        @Override
        public ReservationModel[] newArray(int size) {
            return new ReservationModel[size];
        }
    };

    public String getProtector() {
        return protector;
    }

    public void setProtector(String protector) {
        this.protector = protector;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public Object getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Object reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getAccompanySitu() {
        return accompanySitu;
    }

    public void setAccompanySitu(String accompanySitu) {
        this.accompanySitu = accompanySitu;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(protector);
        dest.writeString(patient);
        dest.writeString(accompany);
        dest.writeString(accompanySitu);
        dest.writeString(hospital);
        dest.writeString(reservationId);
    }
}
