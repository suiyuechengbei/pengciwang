package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Util.DBOpenHelper;
import com.example.myapplication.LoadWordActivity;
import com.example.myapplication.Util.FileUtil;
import com.example.myapplication.Util.JsonHelper;
import com.example.myapplication.dao.Constant;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONStringer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.myapplication.dao.Constant.BASE_URL;
import static com.example.myapplication.dao.Constant.LOGIN;
import static com.example.myapplication.dao.Constant.REGISTER;

/**
 * MainActivity
 * 登录注册
 */
public class MainActivity extends AppCompatActivity {
    private DBOpenHelper mDBOpenHelper;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtUserName;
    private EditText mEtPassword;
    private TextView mTvForget;
    private TextView mTvFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mBtnLogin = findViewById(R.id.btn_1);
        mBtnRegister = findViewById(R.id.btn_2);
        mEtUserName = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);
        mTvForget = findViewById(R.id.tv_1);
        mTvFace = findViewById(R.id.tv_2);
        mDBOpenHelper=new DBOpenHelper(this);
        if(mDBOpenHelper.getAllData().isEmpty()){
            FileUtil.saveLocalJson2Data(Constant.CET4_BOOK_1);
            JsonHelper.analyseDefaultAndSave(FileUtil.readLocalJson(Constant.CET4_BOOK_1));
        }
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=mEtUserName.getText().toString();
                final String password=mEtPassword.getText().toString();
                if(username.isEmpty()){               //登录成功
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String json="{\n" +
                                        "    \"name\":\""+username+"\",\n" +
                                        "    \"password\":\""+password+"\"\n" +
                                        "}";
                                OkHttpClient client=new OkHttpClient();
                                Request request=new Request.Builder()
                                        .url(LOGIN)
                                        .post(RequestBody.create(MediaType.parse("application/json"),json))
                                        .build();
                                Response response=client.newCall(request).execute();
                                String responseData=response.body().string();
                                JSONObject jsonObject=new JSONObject(responseData);
                                int code=jsonObject.getInt("code");
                                if(code==1){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=mEtUserName.getText().toString();
                final String password=mEtPassword.getText().toString();
                if(username.isEmpty()){               //登录成功
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                String json="{\n" +
                                        "    \"name\":\""+username+"\",\n" +
                                        "    \"password\":\""+password+"\"\n" +
                                        "}";
                                OkHttpClient client=new OkHttpClient();
                                Request request=new Request.Builder()
                                        .url(REGISTER)
                                        .post(RequestBody.create(MediaType.parse("application/json"),json))
                                        .build();
                                Response response=client.newCall(request).execute();
                                String responseData=response.body().string();
                                JSONObject jsonObject=new JSONObject(responseData);
                                int code=jsonObject.getInt("code");
                                if(code==1){
                                    mDBOpenHelper.add(username,password);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this, BindActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RetrieveActivity.class);
                startActivity(intent);
            }
        });
        mTvFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
