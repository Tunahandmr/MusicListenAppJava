package com.tunahan.musiclistenappjava;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    // Required empty public constructor
    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1) Inflate the fragment layout (fragment_login.xml)
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // 2) Find views in the inflated layout
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        btnLogin = rootView.findViewById(R.id.btnLogin);

        // 3) Handle button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Basit kontrol
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
                } else {
                    // Örnek bir kontrol (normalde sunucu veya Firebase gibi bir yerle doğrulama yapılır)
                    if (email.equals("test@spotify.com") && password.equals("12345")) {
                        Toast.makeText(requireContext(), "Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                        // Başarılı giriş sonrası başka bir ekrana yönlendirebilirsin
                        // Örneğin başka bir fragment veya Activity'ye geçiş
                    } else {
                        Toast.makeText(requireContext(), "Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 4) Return the fragment's root view
        return rootView;
    }
}
