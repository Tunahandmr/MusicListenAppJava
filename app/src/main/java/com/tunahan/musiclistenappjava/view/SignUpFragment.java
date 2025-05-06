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
import com.tunahan.musiclistenappjava.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        binding.signupButton.setOnClickListener(this::saveNewUser);
    }

    public void saveNewUser(View view) {
        String email = Objects.requireNonNull(binding.emailET.getText()).toString();
        String password = Objects.requireNonNull(binding.passwordET.getText()).toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), getString(R.string.user_registration_successful),
                                Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToFeedFragment());
                    } else {
                        Toast.makeText(requireContext(), Objects.requireNonNull(task.getException().getMessage()).toString(),
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