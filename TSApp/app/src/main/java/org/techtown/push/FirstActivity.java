package org.techtown.push;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import org.techtown.push.ui.main.MainFragment;


public class FirstActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main);
        mainFragment = new MainFragment();

        toolbar.findViewById(R.id.cover_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_LONG).show(); // 토스트는 동작하지만 프래그먼트 전환은 오류

                FragmentManager fragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저 선언
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 프래그먼트 트랜잭션 시작

                /* 여기에서 프래그먼트 트랜잭션, 백스택, 애니메이션 등을 설정합니다. */
                fragmentTransaction.remove(mainFragment);
                fragmentTransaction.replace(R.id.nav_host_fragment,mainFragment); // 프래그먼트 변경
                fragmentTransaction.addToBackStack(null); // 이전 트랜잭션을 백스택에 저장
                fragmentTransaction.commit(); // 프래그먼트 트랜잭션 마무리


                //getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_outer, R.id.nav_top, R.id.nav_bottom,
                R.id.nav_shoes, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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
