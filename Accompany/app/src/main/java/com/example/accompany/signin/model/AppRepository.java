package com.example.accompany.signin.model;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class AppRepository {

    private static final String TAG = "AppRepository";


    private Application mApplication;
    private FirebaseAuth mFirebaseAuth;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;


    //파이어베이스 리얼타임 데이터 베이스 변수들 2개
    //앱이 데이터베이스에 액세스 하기위한 진입점
    private FirebaseDatabase mFirebaseDatabase;
    //데이터베이스의 특정 부분을 참조하는 클래스(데이터베이스중 메시지 부분만 참조 ex. child("message"))
    private DatabaseReference mSignupDatabaseReference;


    public AppRepository(Application application) {
        mApplication = application;

        //데이터베이스 진입점 초기화
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //진입점을 이용해서 db 특정부분에 대한 참조를 얻음 mFirebaseDatabase.getReference() 루트노드/
        //.child("messages")는 메세지 파트를 참조하겠다.
        mSignupDatabaseReference = mFirebaseDatabase.getReference().child("normal_user");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();

    }

    public void register(String email, String password, String name, String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mApplication.getMainExecutor(), new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                                NormalAccount normalAccount = new NormalAccount(email, password, name, phone);
                                mSignupDatabaseReference.push().setValue(normalAccount);
                                Log.d(TAG, mSignupDatabaseReference.getKey());

                            } else {
                                Toast.makeText(mApplication, "Registration Failed"
                                        + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public MutableLiveData<FirebaseUser> getmUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void signin(String email, String password) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mApplication.getMainExecutor(),
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                            } else {
                                Toast.makeText(mApplication, "Sign in failed"
                                        + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void signout() {
        mFirebaseAuth.signOut();
    }
}
