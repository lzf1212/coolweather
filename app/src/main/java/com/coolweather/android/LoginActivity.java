package com.coolweather.android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.coolweather.android.db.MyDBHelper;
import com.coolweather.android.util.SharePreferenceUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mAccount;
    private EditText mPassword;
    private CheckBox mRemember;
    public static AppCompatActivity instance;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new MyDBHelper(this, "UserStore.db", null, 1);
        init();
        instance = this;
    }


    private void init() {
        mAccount = (EditText) findViewById(R.id.edit_login_phone);
        mPassword = (EditText) findViewById(R.id.edit_login_password);
        mRemember = (CheckBox) findViewById(R.id.check_login_remember);
        if (SharePreferenceUtils.getRemember(this)) {
            mRemember.setChecked(true);
            if (null != SharePreferenceUtils.getPhone(this)) {
                mAccount.setText(SharePreferenceUtils.getPhone(this));
            }
            if (null != SharePreferenceUtils.getPassword(this)) {
                mPassword.setText(SharePreferenceUtils.getPassword(this));
            }
        }
        mRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mRemember.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
                } else {
                    mRemember.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.color_word1));
                }
            }
        });

        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tv_login_goRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_login_goRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }


    private void login() {
        if (TextUtils.isEmpty(mAccount.getText().toString())) {
            Toast.makeText(this, R.string.register_toast_noPhone, Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(mPassword.getText().toString())) {
                Toast.makeText(this, R.string.register_toast_noPassword, Toast.LENGTH_SHORT).show();
            } else {

                if (mRemember.isChecked()) {
                    SharePreferenceUtils.writeRememder(  LoginActivity.this, true);
                    SharePreferenceUtils.writePassword(  LoginActivity.this, mPassword.getText().toString());
                } else {
                    SharePreferenceUtils.writeRememder(  LoginActivity.this, false);
                }
                SharePreferenceUtils.writePhone(  LoginActivity.this, mAccount.getText().toString());
                String userName = mAccount.getText().toString();
                String passWord = mPassword.getText().toString();
                if (login(userName, passWord)) {
                    Toast.makeText(  LoginActivity.this, "登陆成功（ZY，111）", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(  LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(  LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }

    //验证登录
    public boolean login(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
