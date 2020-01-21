package org.techtown.push.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.push.Cart;
import org.techtown.push.FirstActivity;
import org.techtown.push.R;
import org.techtown.push.ui.buy.BuyViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    private ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        listView = (ListView) root.findViewById(R.id.listViewToCart);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_cart,fileList);

        listView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("users").child(user.getEmail().replace(".","_")).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("get Key", dataSnapshot.getKey());
                //for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {



                    /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String str = fileSnapshot.child("users").child(user.getEmail().replace(".","_")).child("cart").getValue(String.class);
                    fileList.add(str);*/
              //  }

                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;

    }

}