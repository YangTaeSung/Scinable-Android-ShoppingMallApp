package org.techtown.push;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class RegisterActivity extends AppCompatActivity {

/* Database part

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

*/


    private FirebaseAuth mAuth;

    private EditText editTextEmail, editTextPassword;
    private Button registerButton;


    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.findViewById(R.id.garage_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),FirstActivity.class);
                startActivity(intent);

                /* 타이틀 누르면 처음화면으로 돌아가기 위해 프래그먼트에 관하여 학습(위의 인텐트로 해결)

                FragmentManager fragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저 선언
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 프래그먼트 트랜잭션 시작

                /* 여기에서 프래그먼트 트랜잭션, 백스택, 애니메이션 등을 설정합니다.
                fragmentTransaction.remove(mainFragment).commitAllowingStateLoss(); // 프래그먼트 삭제
                fragmentTransaction.replace(R.id.container,mainFragment); // 프래그먼트 변경
                fragmentTransaction.addToBackStack(null); // 이전 트랜잭션을 백스택에 저장
                fragmentTransaction.commit(); // 프래그먼트 트랜잭션 마무리

                 */

            }

        });


        // 오른쪽 아래에 우표모양버튼 fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout2);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        // navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_outer, R.id.nav_top, R.id.nav_bottom,
                R.id.nav_shoes, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.register_layout);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /* nav_header_main.xml에 이미지나 텍스트뷰가 있을 때 해당 요소의 클릭이벤트리스너 작동방법
        View headView = navigationView.getHeaderView(0);

        ImageView imgView = headView.findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

        TextView textView1 = headView.findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

         */




/* Database part

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String text = dataSnapshot.getValue(String.class);
                // textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //     public void onClick(View v) {
        //          conditionRef.setValue(editText.getText().toString());
        //       }
        //  });

*/


        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword= findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.bt_Register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }

    // When sign up, should conform to forms ( this form -> ******@****.*** )!!!!!!!!!!!
    // Sign up code is only this one ( including email_login_button code in onCreate() )
    private void createUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Toast.makeText(RegisterActivity.this, "Signed up Success", Toast.LENGTH_SHORT).show();
                           // <-- Database part --> conditionRef.setValue(editTextEmail.getText().toString());
                            finish();
                        } else {
                            // Sign up failed -> 1. ID is already exist.
                            //                -> 2. Signed up form is disaccord
                            Toast.makeText(RegisterActivity.this,
                                    "Signed up Failed. The registration form is incorrect. Or the Email is already registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    // 우측 상단의 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // 우측 상단의 메뉴 클릭 시 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {

            case R.id.action_mypage:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
