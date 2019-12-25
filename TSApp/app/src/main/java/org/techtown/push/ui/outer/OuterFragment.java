package org.techtown.push.ui.outer;

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

public class OuterFragment extends Fragment {

    private OuterViewModel outerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        outerViewModel =
                ViewModelProviders.of(this).get(OuterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_outer, container, false);
        final TextView textView = root.findViewById(R.id.text_outer);
        outerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}