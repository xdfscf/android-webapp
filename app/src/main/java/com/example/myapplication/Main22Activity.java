package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.*;

public class Main22Activity extends AppCompatActivity implements View.OnClickListener
         {
    public static final String Tag="Main_activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Button button1=(Button)findViewById(R.id.log_in);
        Button button2=(Button)findViewById(R.id.register);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    public void showWarnSweetDialog(String warning)
    {
        TextView warn=(TextView) findViewById(R.id.warning);
        warn.setText(warning);
    }

    @Override
    public void onClick(View v)
    {
        EditText user_name=(EditText) findViewById(R.id.User_name);
        EditText pass_word=(EditText) findViewById(R.id.Pass_word);
        String userName = user_name.getText().toString();
        String passWord = pass_word.getText().toString();
        if(userName.equals("")||passWord.equals(""))
        {
            showWarnSweetDialog("账号密码不能为空");
            return;
        }
        switch (v.getId())
        {
            case R.id.log_in:
                String url = "http://192.168.253.1:5000/user";
                //getCheckFromServer(url,userName,passWord);
                break;
            case R.id.register:
                String url2 = "http://nightmaremlp.pythonanywhere.com/appnet/register";
                registeNameWordToServer(url2,userName,passWord);
                break;
        }
    }

    private void registeNameWordToServer(String url,final String userName,String passWord){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", userName);
        formBuilder.add("password", passWord);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        final Call call = client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    showWarnSweetDialog("等待结果");
                    Log.d("okhttp_error", "1");
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (res.equals("0")) {
                                    showWarnSweetDialog("该用户名已被注册");
                                    Log.d("okhttp_error", "2");
                                } else if (res.equals("1")) {
                                    /*
                                    showSuccessSweetDialog(res);
                                    sharedPreferences = getSharedPreferences("UserIDAndPassword", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", userName);
                                    editor.apply();*/

                                    Log.d("okhttp_error", res);
                                    showWarnSweetDialog("注册成功");
                                }

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*
        t1.start();
        try {
            t1.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }*/




        /*
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.d("okhttp_error",e.getMessage());
                        showWarnSweetDialog("服务器错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String res = response.body().string();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (res.equals("0"))
                        {
                            showWarnSweetDialog("该用户名已被注册");
                        }
                        else if(res.equals("1"))
                        {
                            showSuccessSweetDialog(res);
                            sharedPreferences = getSharedPreferences("UserIDAndPassword", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", userName);
                            editor.apply();

                            Log.d("okhttp_error",res);
                            showWarnSweetDialog("注册成功");
                        }

                    }
                });
            }
        });*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(Tag,"onStart");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(Tag,"onResume");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(Tag,"onPause");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(Tag,"onStop");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(Tag,"onDestroy");
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(Tag,"onRestart");
    }

}
