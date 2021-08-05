package com.waang.waang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView graduate;
        TextView graduateTxt;
        ImageView mileage;
        TextView mileageTxt;
        ImageView employ;
        TextView employTxt;
        ImageView mypage;
        TextView mypageTxt;

        graduate = (ImageView) findViewById(R.id.graduate);
        graduateTxt = (TextView) findViewById(R.id.graduateTxt);
        mileage = (ImageView) findViewById(R.id.mileage);
        mileageTxt = (TextView) findViewById(R.id.mileageTxt);
        employ = (ImageView) findViewById(R.id.employ);
        employTxt = (TextView) findViewById(R.id.employTxt);
        mypage = (ImageView) findViewById(R.id.mypage);
        mypageTxt = (TextView) findViewById(R.id.mypageTxt);

        userName2 = (TextView) findViewById(R.id.userName2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String numberinfo = user.getEmail();
        userName2.setText(numberinfo.substring(0, 8));

        graduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
        graduateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
        mileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mileage.class);
                startActivity(intent);
            }
        });
        mileageTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mileage.class);
                startActivity(intent);
            }
        });
        employ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Employ.class);
                startActivity(intent);
            }
        });
        employTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Employ.class);
                startActivity(intent);
            }
        });
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
        mypageTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });
    }

}