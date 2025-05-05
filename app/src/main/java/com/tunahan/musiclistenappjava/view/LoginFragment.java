package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBack.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        binding.signInButton.setOnClickListener(v -> signIn());
        binding.forgotPasswordText.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgotPasswordFragment));
    }

    private void signIn() {
        String email = Objects.requireNonNull(binding.emailET.getText()).toString();
        String password = Objects.requireNonNull(binding.passwordET.getText()).toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_feedFragment);
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.authentication_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
