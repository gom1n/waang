package com.waang.waang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MyPage extends AppCompatActivity {

    private TextView userName;
    private TextView contact;
    private TextView notice;
    private TextView consent;
    private TextView service;
    private TextView version;
    private TextView dropOut;
    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userName = (TextView)findViewById(R.id.userName);
        contact = (TextView)findViewById(R.id.contact);
        notice=(TextView)findViewById(R.id.notice);
        consent = (TextView)findViewById(R.id.consent);
        service = (TextView)findViewById(R.id.service);
        version = (TextView)findViewById(R.id.version);
        dropOut = (TextView)findViewById(R.id.dropOut);
        logoutBtn = (Button)findViewById(R.id.logoutBtn);

        userName = (TextView) findViewById(R.id.userName);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String numberID = user.getEmail();
        userName.setText(numberID.substring(0,8));

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyPage.this);
                dlg.setTitle("문의하기");
                /*final String[] contactArray = new String[]{"전화 문의", "이메일 문의"};
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setSingleChoiceItems(contactArray, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                })*/
                CharSequence contactArray[] = new CharSequence[]{"이메일 문의하기", "블로그 문의하기"};
                dlg.setItems(contactArray, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        switch(which){
                            case 0:
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                String[] address = {"dumpit2021@gmail.com"};
                                email.putExtra(Intent.EXTRA_EMAIL, address);
                                email.putExtra(Intent.EXTRA_SUBJECT, "[와앙][문의하기] ");
                                email.putExtra(Intent.EXTRA_TEXT, "[문의 내용]\n");
                                startActivity(email);
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://waangsungshin.blogspot.com/2021/07/blog-post.html"));
                                startActivity(intent);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dlg.show();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
            }
        });
        consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Consent.class);
                startActivity(intent);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Service.class);
                startActivity(intent);
            }
        });
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Version.class);
                startActivity(intent);
            }
        });
        dropOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getCurrentUser().delete();
                Intent intent = new Intent(MyPage.this, Login.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MyPage.this, Login.class);
                startActivity(intent);
            }
        });
    }
}