package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.store3.R;
import com.example.administrator.store3.util.Pub;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2016/5/24.
 */
public class ZhaoHuiMiMaActivityy extends Activity implements View.OnClickListener{
    private ImageView imageView;
    private CheckBox checkBox1,checkBox2;
    private Button button1,button2;
    private String email;
    private EditText editText1,editText2,editText3;
    private boolean anniu2=false;
    private boolean anniu1=false;
    //验证码
    private String valnum;
    //密码
    private String password;
    public static volatile String localCookie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaohuimima);
        setViews();
    }
    private void setViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        checkBox1= (CheckBox) findViewById(R.id.checkBox1);
        checkBox2= (CheckBox) findViewById(R.id.checkBox2);
        button1= (Button) findViewById(R.id.btn1);
        button2= (Button) findViewById(R.id.btn2);
        editText1= (EditText) findViewById(R.id.editPhone);
        editText2= (EditText) findViewById(R.id.editEaiml);
        editText3= (EditText) findViewById(R.id.editYanma);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                ZhaoHuiMiMaActivityy.this.finish();
                break;
            case R.id.checkBox1:
                break;
            case R.id.checkBox2:
                break;
            case R.id.btn1:    //获取验证码
                if (!isValid())
                    break;
                getRegister();
                break;
            case R.id.btn2:     //下一步
               getRegisters();
                break;
        }
    }
    //下一步跳转
    private void  getRegisters(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String login_url= Pub.SPOP_URI+"password/validate";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("TAG", "response=" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String  state= (String) jsonObject.get("state");
                    String resultMessage= (String) jsonObject.get("resultMessage");
                    Log.e("AAAA","jsonObject="+jsonObject.toString());
                   Log.e("AAAAHQQQH", "sresultMessage=" + resultMessage.toString());
                  if (Integer.parseInt(state)==0){
                     Intent intent=new Intent(ZhaoHuiMiMaActivityy.this,ResetPasswordActivity.class);
                        startActivity(intent);
                    }else if(Integer.parseInt(state)==1){
                        Toast.makeText(ZhaoHuiMiMaActivityy.this,"验证码出错",Toast.LENGTH_SHORT).show();
                    }else{
                      Toast.makeText(ZhaoHuiMiMaActivityy.this,"验证码超时",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("TAG",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
                Toast.makeText(ZhaoHuiMiMaActivityy.this,"验证失败",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>   registerParams = new HashMap<>();
                valnum=editText3.getText().toString().trim();
                registerParams.put("valnum", valnum);
                return   registerParams;
            }
            //设置请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(localCookie!=null &&localCookie.length()>0){
                    HashMap<String,String> headers=new HashMap<>();
                    headers.put("cookie",localCookie);
                    return headers;
                }else {
                    return super.getHeaders();
                }

            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }
    //获取验证码
    private void  getRegister(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String login_url= Pub.SPOP_URI+"password/mail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                  Log.e("TAG4444", "response=" + response);

                    JSONObject jsonObject = new JSONObject(response);
                    String session=(String)jsonObject.get("sessionid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);

                Toast.makeText(ZhaoHuiMiMaActivityy.this,"验证失败",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>   registerParams = new HashMap<>();
                registerParams.put("email", email);                             //获取邮箱
                return   registerParams;
            }
            //重写parseNetworkResponse方法，返回的数据中 Set-Cookie:为session id
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
               Response<String> superResponse = super .parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
               localCookie = rawCookies.substring(0, rawCookies.indexOf(";"));
                return superResponse;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);

    }


    public boolean isValid() {

        email=editText2.getText().toString().trim();
        anniu2=checkBox2.isChecked();

        if(email.equals("")){
            Toast.makeText(this, "邮箱输入不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pswFilter(email, Pub.EMAL_REGEX)) {
            Toast.makeText(this, "邮箱输入有误", Toast.LENGTH_SHORT).show();
            return false;
        }/* if (anniu2==false) {
            Toast.makeText(this, "您选择了邮箱地址", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private boolean pswFilter(CharSequence s,String string) {
        if (TextUtils.isEmpty(s)) {
            return true;
        }
        Pattern pattern = Pattern.compile(string);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


}
