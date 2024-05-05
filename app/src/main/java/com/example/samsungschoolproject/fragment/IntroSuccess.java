package com.example.samsungschoolproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.activity.MainActivity;

public class IntroSuccess extends Fragment {

    public IntroSuccess() {}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startMainActivityButton = view.findViewById(R.id.buttonOpenMainActivity);

        startMainActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish(); // Закрыть текущую активити
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_success, container, false);
    }
}
