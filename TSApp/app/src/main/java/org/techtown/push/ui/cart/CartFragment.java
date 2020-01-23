package org.techtown.push.ui.cart;

import android.content.Context;
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

    private Context mContext;

    private CartViewModel cartViewModel;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    int totalPrice = 0;

    LinearLayout linearMain;
    CheckBox checkBox;

    ArrayList<String> valueList;
    ArrayList<String> keyList;
    ArrayList<CheckBox> checkBoxes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            final TextView totalCosts = (TextView) root.findViewById(R.id.text_totalCosts);
            final Button deleteButton = (Button) root.findViewById(R.id.button2);

            linearMain = (LinearLayout) root.findViewById(R.id.linear_main);
            valueList = new ArrayList<String>();
            keyList = new ArrayList<String>();
            checkBoxes = new ArrayList<CheckBox>();

            totalPrice = 0;

            databaseReference.child(user.getEmail().replace(".", "_")).child("cart").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    linearMain.removeAllViews();
                    valueList.clear();
                    keyList.clear();
                    checkBoxes.clear();
                    totalPrice = 0;

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        valueList.add(child.getValue(String.class));
                        keyList.add(child.getKey());

                        Log.d("get Key", child.getKey());
                        Log.d("get Value", child.getValue(String.class));

                    }

                    for (int i = 0; i < valueList.size(); i++) {

                        checkBox = new CheckBox(mContext);
                        checkBox.setId(i);
                        totalPrice += CalculateTotalCosts(valueList.get(i));
                        checkBox.setText(valueList.get(i));
                        checkBox.setChecked(true);
                        checkBox.setOnClickListener(getOnClickDoSomething(checkBox, totalCosts));
                        checkBoxes.add(checkBox);
                    /*
                        checkBox.setOnClickListener((new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Log.d("checkBox", "id" + v.getId());
                            int totalPriceChanged = totalPrice - CalculateTotalCosts(valueList.get(v.getId()));
                            totalCosts.setText("총 주문금액 : " + totalPriceChanged + "원");

                        }

                    }));
                    */
                        linearMain.addView(checkBox);

                    }

                    totalCosts.setText("총 주문금액 : " + totalPrice + "원");

                    deleteButton.setOnClickListener(getOnClickDeleteListener(checkBoxes, databaseReference, keyList));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);

        }

        return root;

    }


    // CheckBox의 클릭리스너
    View.OnClickListener getOnClickDoSomething(final Button button, final TextView textView) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log.d("checkBox", "id" + button.getId());
                CheckBox checkBox = (CheckBox) button;
                if(!checkBox.isChecked()) {

                    int totalPriceChanged = totalPrice - CalculateTotalCosts(button.getText().toString());
                    totalPrice = totalPriceChanged;
                    textView.setText("총 주문금액 : " + totalPriceChanged + "원");

                } else {

                    int totalPriceChanged = totalPrice + CalculateTotalCosts(button.getText().toString());
                    totalPrice = totalPriceChanged;
                    textView.setText("총 주문금액 : " + totalPriceChanged + "원");

                }

            }

        };

    }


    View.OnClickListener getOnClickDeleteListener(final ArrayList<CheckBox> checkBoxes, final DatabaseReference databaseReference, final ArrayList<String> keyList) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                for(CheckBox cb : checkBoxes) {

                    if(cb.isChecked()) {

                        databaseReference.child(user.getEmail().replace(".","_")).child("cart").child(keyList.get(cb.getId())).setValue(null);

                    }

                }

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


    /* Fragment에서 getContext()나 getActivity() 사용 시 null을 반환하는 오류가 발생
     * onDataChange 부분에서 getContext()나 getActivity() 사용 했을 때, null 반환 오류 발생
     * (아이템을 장바구니에 담을 때 처음에는 작동하지만 두 번째부터 오류 발생)
     * 프래그먼트가 있는 액티비티의 수명 주기는 해당 프래그먼트의 수명 주기에 직접적인 영향을
     * 미치기 때문에 액티비티에 대한 각 수명 주기 콜백이 각 프래그먼트에 대한 비슷한
     * 콜백을 유발해야 한다. 즉, 액티비티에 첨부되지 않았기 때문에 계속 null을 반환했던 것이다.
     * 따라서 Fragment가 액티비티에 추가될 때 호출되어 액티비티를 받아오는 콜백함수
     * OnAttach 함수를 따로 오버라이딩 해야한다.
     * */
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;

    }

}