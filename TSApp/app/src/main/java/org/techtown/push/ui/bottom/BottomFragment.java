package org.techtown.push.ui.bottom;

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

public class BottomFragment extends Fragment {

    private BottomViewModel bottomViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bottomViewModel =
                ViewModelProviders.of(this).get(BottomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bottom, container, false);
        final TextView textView = root.findViewById(R.id.text_bottom);
        bottomViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}