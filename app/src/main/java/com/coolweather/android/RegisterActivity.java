package com.coolweather.android;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coolweather.android.db.MyDBHelper;
import com.coolweather.android.util.SharePreferenceUtils;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mAccount;
    private EditText mPassword;
    private EditText mAgain;
    private MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
        init();
    }


    private void init() {
        mAccount = (EditText) findViewById(R.id.edit_login_phone);
        mPassword = (EditText) findViewById(R.id.edit_login_password);
        mAgain= (EditText) findViewById(R.id.edit_passwordAgain);
        findViewById(R.id.tv_register).setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                register();
                break;
        }
    }



    private void register() {
        if (TextUtils.isEmpty(mAccount.getText().toString())) {
            Toast.makeText(this, R.string.register_toast_noPhone, Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(mPassword.getText().toString())) {
                Toast.makeText(this, R.string.register_toast_noPassword, Toast.LENGTH_SHORT).show();
            } else {
                if (!mPassword.getText().toString().equals(mAgain.getText().toString())){
                    Toast.makeText(this, R.string.register_same, Toast.LENGTH_SHORT).show();
                }else{
                    if (CheckIsDataAlreadyInDBorNot(mAccount.getText().toString())) {
                        Toast.makeText(this,"该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (register(mAccount.getText().toString(), mPassword.getText().toString())) {
                            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(   RegisterActivity.this, MainActivity.class));
                            LoginActivity.instance.finish();
                            finish();
                            SharePreferenceUtils.writePhone(   RegisterActivity.this, mAccount.getText().toString());
                        }
                    }
                }

            }
        }
    }
    //向数据库插入数据
    public boolean register(String username, String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        /*String sql = "insert into userData(name,password) value(?,?)";
        Object obj[]={username,password};
        db.execSQL(sql,obj);*/
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }
    //检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

}
