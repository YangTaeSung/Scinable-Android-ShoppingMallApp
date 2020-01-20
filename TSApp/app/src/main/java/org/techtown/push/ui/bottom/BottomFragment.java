package org.techtown.push.ui.bottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import org.techtown.push.R;
import org.techtown.push.ui.top.CustomOnItemSelectedListener;
import org.techtown.push.ui.top.TopViewModel;

public class BottomFragment extends Fragment {

    private BottomViewModel bottomViewModel;

    private Spinner spinner;


    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bottomViewModel = ViewModelProviders.of(this).get(BottomViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bottom, container, false);

        spinner = root.findViewById(R.id.planets_spinner_d);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        /* 기존에 있던 코드. 이 프래그먼트가 보여질 때, TopViewModel에 설정되어 있는 텍스트를
            가져와 xml파일에 공백으로 설정해놓은 TextView에 setText()한다.

        final TextView textView = root.findViewById(R.id.text_top);
        topViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        */


        /* onClick method test. it has to using *root*

        TextView textView = root.findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"ff",Toast.LENGTH_LONG).show();

            }

        });

        */

        // 구매하기 버튼에 대한 동작
        Button button = root.findViewById(R.id.sell_button_d);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(String.valueOf(spinner.getSelectedItem()).equals("선택")) { // 스피너의 옵션을 선택하지 않은 경우

                    Toast.makeText(getActivity(), "옵션을 선택해주세요.", Toast.LENGTH_LONG).show();

                } else { // 구매하는 페이지로 넘어가기

                    // Action으로 Fragment 전환
                    String spinnerItem = spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("Selected item", spinnerItem);
                    Navigation.findNavController(v).navigate(R.id.action_nav_bottom_to_nav_buy,bundle);

                    Toast.makeText(getActivity(), "good", Toast.LENGTH_LONG).show();

                }

            }

        });


        // 장바구니 담기버튼에 대한 동작
        Button button2 = root.findViewById(R.id.cart_button_d);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(String.valueOf(spinner.getSelectedItem()).equals("선택")) { // 스피너의 옵션을 선택하지 않은 경우

                    Toast.makeText(getActivity(), "옵션을 선택해주세요.", Toast.LENGTH_LONG).show();

                } else { // 장바구니에 담기는 동작

                    Navigation.findNavController(v).navigate(R.id.action_nav_bottom_to_nav_cart);

                    Toast.makeText(getActivity(), "good", Toast.LENGTH_LONG).show();

                }

            }

        });

        return root;
    }

    public void addListenerOnSpinnerItemSelection() {


    }
}