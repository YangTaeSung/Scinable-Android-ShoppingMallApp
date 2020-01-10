package org.techtown.push.ui.top;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.techtown.push.R;

import static android.R.layout.simple_spinner_item;

public class TopFragment extends Fragment {

    private TopViewModel topViewModel;

    private Spinner spinner;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        topViewModel = ViewModelProviders.of(this).get(TopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top, container, false);

        spinner = root.findViewById(R.id.planets_spinner);
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

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"ff",Toast.LENGTH_LONG).show();

            }

        });

        return root;
    }

    public void addListenerOnSpinnerItemSelection() {


    }
}