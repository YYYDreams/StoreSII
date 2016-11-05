package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.store3.MainActivity;
import com.example.administrator.store3.R;
import com.example.administrator.store3.util.Pub;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageView imageView;
    private EditText editText1,editText2;
    private Button button;
    private TextView textView0,textView1;
    private String username;
    private String password;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu_main);
        setViews();
       //获取sessionid
       sp=getSharedPreferences("config", Context.MODE_PRIVATE);
    }
    private void setViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        editText1= (EditText) findViewById(R.id.editText1);
        editText2= (EditText) findViewById(R.id.editText2);
        textView0= (TextView) findViewById(R.id.t_View0);
        textView0.setOnClickListener(this);
        textView1= (TextView) findViewById(R.id.t_View1);
        textView1.setOnClickListener(this);
        button= (Button) findViewById(R.id.login_button);
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                if(!isValid())
                    break;
                getLogins();
                break;
            case R.id.t_View0:
                Intent intent0=new Intent(this,ZhuCeActivity.class);
                startActivity(intent0);
                break;
            case R.id.image0:
                LoginActivity.this.finish();
                break;
            case R.id.t_View1:
                Intent intent1=new Intent(this,ZhaoHuiMiMaActivityy.class);
                startActivity(intent1);
                break;
        }
    }
    /**
     * 登录
     */
    private void   getLogins(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String login_url= Pub.SPOP_URI+"login/submit";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("TAG", "response="+response);
                    //使用JSONObject给response转换编码
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean state= (Boolean) jsonObject.get("state");
                    String resultMessage= (String) jsonObject.get("resultMessage");
                    String session= (String) jsonObject.get("session");

                    //复值并保存sessionid
               /*     Pub.mSESSION=session;
                    sp.edit().putString("SESSION",Pub.mSESSION).commit();*/

                    if(state==true){
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                    }else
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        }){
            //发送参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> registerParams = new HashMap<>();
                registerParams.put("username",username);
                registerParams.put("password", password);
                return   registerParams;
            }

          @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> superResponse=super.parseNetworkResponse(response);
                Map<String,String> responseHeaders=response.headers;
                String rawCookies=responseHeaders.get("Set-Cookie");
                Pub.mSESSION=rawCookies.substring(0,rawCookies.indexOf(";"));
              //登录是保存
                sp.edit().putString("SESSION",Pub.mSESSION).commit();
                Log.e("1"," Pub.mSESSION="+ Pub.mSESSION);

                return superResponse;

            }

            //请求头
           @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> headers = new HashMap<>();
               headers.put("Accept", "application/json");
               //headers.put("Content-Type","application/json; charset=UTF-8");
               headers.put("Content-Type","application/x-www-form-urlencoded");
               return headers;
           }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }



    public boolean isValid() {
        username=editText1.getText().toString().trim();
        password=editText2.getText().toString().trim();

        if (username.equals("")) {
            Toast.makeText(this, "用户名/邮箱/手机不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }if (password.equals("")) {
            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }/*else if (password.equals(password)) {
            Toast.makeText(this, "密码不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

}

