package com.waang.waang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    //기능 구현 이후 삭제 필요
    private EditText login_id;
    private EditText login_pw;
    private EditText pw_check;

    private  TextInputLayout pw1;
    private TextInputLayout pw2;

    Button register;
    // Button main;
    private FirebaseAuth firebaseAuth;  //파이어베이스 인증 처리
    private DatabaseReference DatabaseRef; // 실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("waang");

        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);
        pw_check = findViewById(R.id.pw_check);
        register = (Button)findViewById(R.id.register);
        pw1 = findViewById(R.id.PW1);
        pw2 = findViewById(R.id.PW2);

        register.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View view) {

                String idorigin = login_id.getText().toString();
                String id = login_id.getText().toString() + "@sungshin.ac.kr";
                String pw = login_pw.getText().toString();
                String pwcheck = pw_check.getText().toString();
                int indexes = id.indexOf("@");
                String info = id.substring(0, indexes);

                if(idorigin.length() !=0 &&idorigin.length()!=8){
                    Toast.makeText(Register.this, "학번을 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pw)&& TextUtils.isEmpty(idorigin)) {
                    Toast.makeText(Register.this, "학번과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(idorigin)) {
                    Toast.makeText(Register.this, "학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pw)) {
                    Toast.makeText(Register.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pwcheck)) {
                    Toast.makeText(Register.this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setIdinfo(firebaseUser.getEmail());
                            account.setPwinfo(pw);

                            // database에 insert
                            DatabaseRef.child("UserAccount").child(info).setValue(account);
                            Toast.makeText(Register.this, "환영합니다" + idorigin + " 수정님!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);

                        } else {

                            if(!pwcheck.equals(pw))
                                pw2.setError("비밀번호가 일치하지 않습니다.");
                            else
                                pw2.setError(null);     // 비밀번호가 일치할경우 에러 메세지 지움

                            if(id.length() !=8 && pw.length()<6)
                                Toast.makeText(Register.this, "학번과 비밀번호를 다시 확인해주세요!", Toast.LENGTH_SHORT).show();

                            if( pw.length() < 6)
                                pw1.setError("비밀번호를 6자리 이상 입력해주세요!");
                            else
                                pw1.setError(null);
                        }

                    }
                });
            }
        });




    }
}
