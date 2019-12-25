package org.techtown.push.ui.shoes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.techtown.push.R;

public class ShoesFragment extends Fragment {

    private ShoesViewModel shoesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoesViewModel =
                ViewModelProviders.of(this).get(ShoesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoes, container, false);
        final TextView textView = root.findViewById(R.id.text_shoes);
        shoesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
