package com.tunahan.musiclistenappjava;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment {

    private Button btnSignUpFree, btnContinueGoogle, btnContinueFacebook, btnContinueApple;
    private TextView tvLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Do any non-UI setup here (if needed).
        // Do NOT call setContentView(...) in a Fragment.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1) Inflate the fragment's layout.
        //    Make sure fragment_main.xml has your buttons and text views.
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // 2) Find views from the inflated layout
        btnSignUpFree = rootView.findViewById(R.id.btnSignUpFree);
        btnContinueGoogle = rootView.findViewById(R.id.btnContinueGoogle);
        tvLogin = rootView.findViewById(R.id.tvLogin);

        // 3) Set click listeners
        btnSignUpFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign Up Free button
                Toast.makeText(requireContext(), "Sign Up Free tıklandı!", Toast.LENGTH_SHORT).show();
                // Navigate to a sign-up screen, etc.
            }
        });

        btnContinueGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Continue with Google
                Toast.makeText(requireContext(), "Google ile devam et tıklandı!", Toast.LENGTH_SHORT).show();
                // Google Sign-In logic here
            }
        });

        btnContinueFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Continue with Facebook
                Toast.makeText(requireContext(), "Facebook ile devam et tıklandı!", Toast.LENGTH_SHORT).show();
                // Facebook Sign-In logic here
            }
        });

        btnContinueApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Continue with Apple
                Toast.makeText(requireContext(), "Apple ile devam et tıklandı!", Toast.LENGTH_SHORT).show();
                // Apple Sign-In logic here
            }
        });

        // 4) Return the root view for this fragment
        return rootView;
    }
}
