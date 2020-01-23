package org.techtown.push.ui.buy;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.techtown.push.MainActivity;
import org.techtown.push.R;
import org.techtown.push.ui.bottom.BottomViewModel;

public class BuyFragment extends Fragment {

    private BuyViewModel buyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        buyViewModel = ViewModelProviders.of(this).get(BuyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_buy, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            TextView textView = root.findViewById(R.id.text_buy);
            textView.setText(getArguments().getString("Selected item"));

            TextView textView2 = root.findViewById(R.id.text_buy2);

            textView2.setText("Name : " + user.getDisplayName());

            TextView textView3 = root.findViewById(R.id.text_buy3);
            textView3.setText("Email : " + user.getEmail());

        return root;
    }
}