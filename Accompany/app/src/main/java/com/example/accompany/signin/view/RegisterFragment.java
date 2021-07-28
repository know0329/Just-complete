package com.example.accompany.signin.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accompany.R;
import com.example.accompany.databinding.FragmentRegisterBinding;
import com.example.accompany.databinding.FragmentSignInBinding;
import com.example.accompany.signin.viewmodel.SiginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class RegisterFragment extends Fragment {

    //뷰들
    private FragmentRegisterBinding binding;
    private EditText emailEt;
    private EditText passwordEt;
    private EditText nameEt;
    private EditText phoneEt;
    private Button regBtn;

    //뷰모델
    private SiginRegisterViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SiginRegisterViewModel.class);
        viewModel.getmUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        emailEt = binding.regEmail;
        passwordEt = binding.regPassword;
        nameEt = binding.regName;
        phoneEt = binding.regPhoneNumber;
        regBtn = binding.regBtn;

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                String name = nameEt.getText().toString();
                String phone = phoneEt.getText().toString();

                if (email.length() > 0 && password.length() > 0) {
                    viewModel.register(email, password, name, phone);
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_signInFragment);
                }


            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}