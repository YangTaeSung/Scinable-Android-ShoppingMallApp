package org.techtown.push.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.push.MainActivity;
import org.techtown.push.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    int totalPrice = 0;
    int[] checkListPrice;

    LinearLayout linearMain;
    CheckBox checkBox;

    //ListView listView;
    ArrayList<String> valueList;
    //ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        final TextView totalCosts = (TextView) root.findViewById(R.id.text_totalCosts);

        linearMain = (LinearLayout)root.findViewById(R.id.linear_main);
        valueList = new ArrayList<String>();

        totalPrice = 0;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(user.getEmail().replace(".","_")).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    valueList.add(child.getValue(String.class));

                    Log.d("get Value", child.getValue(String.class));

                }

                for(int i = 0; i < valueList.size(); i++) {

                    checkBox = new CheckBox(getActivity());
                    checkBox.setId(i);
                    totalPrice += CalculateTotalCosts(valueList.get(i));
                    checkBox.setText(valueList.get(i));
                    checkBox.setChecked(true);
                    checkBox.setOnClickListener(getOnClickDoSomething(checkBox, totalCosts));
                    /*checkBox.setOnClickListener((new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Log.d("checkBox", "id" + v.getId());
                            int totalPriceChanged = totalPrice - CalculateTotalCosts(valueList.get(v.getId()));
                            totalCosts.setText("총 주문금액 : " + totalPriceChanged + "원");

                        }

                    }));*/
                    linearMain.addView(checkBox);

                }

                totalCosts.setText("총 주문금액 : " + totalPrice + "원");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;

    }


    // CheckBox의 클릭리스너
    View.OnClickListener getOnClickDoSomething(final Button button, final TextView textView) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log.d("checkBox", "id" + button.getId());
                int totalPriceChanged = totalPrice - CalculateTotalCosts(button.getText().toString());
                totalPrice = totalPriceChanged;
                textView.setText("총 주문금액 : " + totalPriceChanged + "원");


            }

        };

    }


    public int CalculateTotalCosts(String productName) {

        int start = 0;
        int end = 0;
        String substr;

        start = productName.indexOf("(");
        end = productName.indexOf(")");
        substr = productName.substring(start+1,end-1);

        return Integer.parseInt(substr);

    }

}