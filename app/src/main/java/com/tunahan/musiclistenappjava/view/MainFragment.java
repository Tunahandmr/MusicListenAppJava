package com.tunahan.musiclistenappjava.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_feedFragment);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSignIn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_loginFragment);
        });

        binding.buttonSignUp.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_signUpFragment);
        });

        binding.buttonSignInWithGoogle.setOnClickListener(v -> {
            signInWithGoogle();
        });

    }

    private static final int RC_SIGN_IN = 9001; // İstek kodu

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { // Google Sign-In işlemi sonucu döndü mü?
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String idToken = account.getIdToken();

                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(requireActivity(), task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String displayName = user.getDisplayName();
                                Toast.makeText(requireContext(), "Welcome " + displayName, Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_feedFragment);
                            } else {
                                Toast.makeText(requireContext(), "Sign-in failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (ApiException e) {
                Toast.makeText(requireContext(), "Google Sign-in Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void signInWithGoogle() {
        String clientId = getString(R.string.client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
