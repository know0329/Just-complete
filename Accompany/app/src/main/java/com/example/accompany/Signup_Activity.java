package com.example.accompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.accompany.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText)findViewById(R.id.signup_edittext_email);
        name = (EditText)findViewById(R.id.signup_edittext_name);
        password = (EditText)findViewById(R.id.signup_edittext_password);
        signup = (Button) findViewById(R.id.signup_button_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //하나라도 입력이 안될경우
                if(email.getText().toString()==null || name.getText().toString()==null ||password.getText().toString()==null  ){
                    return;
                }

                //Firebase Auth서비스 이용
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(Signup_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {//회원가입 완료

                                //username만 데이터 베이스에 입력 이거 전에 model파일 생성 userModel 생성
                                UserModel userModel = new UserModel();
                                userModel.userName = name.getText().toString();
                                userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                //파이어 베이스에 입력
                                String uid = task.getResult().getUser().getUid();
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);

                            }
                        });
            }
        });
    }
}