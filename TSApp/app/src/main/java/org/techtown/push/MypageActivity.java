package org.techtown.push;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MypageActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("201Garage MyPage") ;

        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView name_text = findViewById(R.id.name_text);
        TextView mail_text = findViewById(R.id.mail_text);


        name_text.setText(user.getDisplayName());
        mail_text.setText(user.getEmail());

        database.getReference().child("users").child(user.getEmail().replace(".","_")).child("phoneNumber").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TextView call_text = findViewById(R.id.call_text);
                call_text.setText("" + dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        Button button = findViewById(R.id.signout_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // User delete at Profile

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Log.d("MypageActivity", "User account deleted");
                                }
                            }
                        });
                FirebaseAuth.getInstance().signOut(); // Sign out

                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(intent);

            }

        });

    }

}