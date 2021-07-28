package com.example.accompany.signin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.accompany.signin.model.AppRepository;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class SiginRegisterViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public SiginRegisterViewModel(@NonNull @NotNull Application application) {
        super(application);

        mAppRepository = new AppRepository(application);
        mUserMutableLiveData = mAppRepository.getmUserMutableLiveData();

    }

    public MutableLiveData<FirebaseUser> getmUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void register(String email, String password, String name, String phone) {
        mAppRepository.register(email, password, name, phone);
    }

    public void signin(String email, String password) {
        mAppRepository.signin(email,password);
    }

}
