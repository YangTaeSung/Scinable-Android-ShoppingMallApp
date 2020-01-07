package org.techtown.push.ui.outer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.techtown.push.FirstActivity;
import org.techtown.push.R;
import org.techtown.push.ui.main.MainFragment;
import org.techtown.push.ui.main.MainViewModel;

public class OuterFragment extends Fragment {

    private OuterViewModel outerViewModel;
    MainFragment mainFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        outerViewModel = ViewModelProviders.of(this).get(OuterViewModel.class);

        // 프래그먼트를 액티비티에 추가하는 방법은 1.코드(java) 2.태그(xml)이 있는데 여기서는 태그로 추가됨 (mobile_navigation)
        // rootView라는 뷰그룹 객체가 생성되고 이를 반환하는게 프래그먼트 기본사항
        final View rootView = inflater.inflate(R.layout.fragment_outer, container, false);

        ImageView imageView = rootView.findViewById(R.id.product);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("ff","dddd"); // 정상작동
                MainFragment mainFragment = new MainFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, mainFragment);
                fragmentTransaction.commit();

            }

        });

        final TextView textView = rootView.findViewById(R.id.text_outer);
        outerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return rootView;
    }
}