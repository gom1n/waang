package com.waang.waang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class MyPage extends AppCompatActivity {

    private TextView userName;
    private TextView contact;
    private TextView notice;
    private TextView consent;
    private TextView service;
    private TextView version;
    private TextView dropOut;
    private Button logoutBtn;
    private Button changepwBtn;

    private Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

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
        changepwBtn = (Button)findViewById(R.id.changepwBtn);


        userName = (TextView) findViewById(R.id.userName);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String numberID = user.getEmail();
        int indexes = numberID.indexOf("@");
        String id = numberID.substring(0, indexes);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("waang/UserAccount/" + id);
        mReference.child("names").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String names = snapshot.getValue(String.class);
                userName.setText(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        changepwBtn.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String email = user.getEmail();

            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MyPage.this);
                        dlg.setTitle("Checking");
                        dlg.setMessage("???????????? ????????? ????????? ?????????????????????.");
                        dlg.setPositiveButton("??????", null);
                        dlg.show();
                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MyPage.this);
                        dlg.setTitle("Checking");
                        dlg.setMessage("???????????? ????????? ?????? ????????? ?????????????????????.");
                        dlg.setPositiveButton("??????", null);
                        dlg.show();
                    }
                });
            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyPage.this);
                dlg.setTitle("????????????");

                CharSequence contactArray[] = new CharSequence[]{"????????? ????????????", "????????? ????????????"};
                dlg.setItems(contactArray, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        switch(which){
                            case 0:
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                String[] address = {"dumpit2021@gmail.com"};
                                email.putExtra(Intent.EXTRA_EMAIL, address);
                                email.putExtra(Intent.EXTRA_SUBJECT, "[??????][????????????] ");
                                email.putExtra(Intent.EXTRA_TEXT, "[?????? ??????]\n");
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

                CustomDialog dialog = new CustomDialog(MyPage.this);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

class CustomDialog extends Dialog{

    private Button dialogbye;
    private Button dialogcancle;
    private Context mContext;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        dialogbye = (Button)findViewById(R.id.dialogbye);
        dialogcancle = (Button)findViewById(R.id.dialogcancle);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        dialogbye.setOnClickListener(new View.OnClickListener() {

            public void onClick(View views) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String numberID = user.getEmail();
                int indexes = numberID.indexOf("@");
                String id = numberID.substring(0, indexes);

//                FirebaseAuth.getInstance().signOut();

//                mDatabase = FirebaseDatabase.getInstance();
//                mReference = mDatabase.getReference("waang/UserAccount/" + id);
//                mReference.removeValue();

                firebaseAuth.getCurrentUser().delete();
                Toast.makeText(mContext, "?????????????????????.", Toast.LENGTH_SHORT).show();
                Intent intents = new Intent(mContext, MainActivity.class);
                views.getContext().startActivity(intents);

//                Intent intent = new Intent(mContext, Login.class);
//                intent.putExtra("isCurrent", false);
//                views.getContext().startActivity(intent);
//
//                System.exit(0);
//                dismiss();
//                Intent intents = new Intent(mContext, Login.class);
//                views.getContext().startActivity(intents);
            }
        });

        dialogcancle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                dismiss();
                return;
            }
        });

    }
    public CustomDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


}