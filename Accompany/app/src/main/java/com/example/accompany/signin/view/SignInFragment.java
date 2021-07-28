package com.example.accompany.signin.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accompany.R;
import com.example.accompany.databinding.FragmentSignInBinding;
import com.example.accompany.signin.viewmodel.SiginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private EditText emailEt;
    private EditText passwordEt;
    private Button signinBtn;

    //뷰모델
    private SiginRegisterViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SiginRegisterViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        emailEt = binding.loginEmail;
        passwordEt = binding.loginPassword;
        signinBtn = binding.loginButton;

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (email.length()>0&&password.length()>0) {
                    viewModel.signin(email, password);
                }
            }
        });
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_registerChooseFragment);
            }
        });
    }


}