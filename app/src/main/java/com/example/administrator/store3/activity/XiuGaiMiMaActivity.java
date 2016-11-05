package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
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
public class XiuGaiMiMaActivity extends Activity implements View.OnClickListener {
    private EditText editText1,editText2,editText3;
    private Button button1,button2,button3,button4;
    //新密码
    private String newPassword;
    //重复新密码
    private String newPassword1;
    //旧密码
    private String password;
    private String jsession;
    //旧密码显示的状态变量
    private boolean mbDisplayFlg1 = false;
    //新密码显示的状态变量
    private boolean mbDisplayFlg2 = false;
    //重复密码显示的状态变量
    private boolean mbDisplayFlg3 = false;
    private   CharSequence charSequence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiugaimima);
        setViews();
    }
    //查找控件
    private void setViews() {
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        button1    = (Button) findViewById(R.id.btn1);
        button2    = (Button) findViewById(R.id.btn2);
        button3    = (Button) findViewById(R.id.btn3);
        button4    = (Button) findViewById(R.id.btn4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText1:
                break;
            case R.id.editText2:
                break;
            case R.id.editText3:
            break;
            case R.id.btn1:
                //点击密码切换为可见可隐藏的状态
                if(!mbDisplayFlg1){
                    //设置EditText文本可见
                    editText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //设置EditText文本为隐藏
                    editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg1=!mbDisplayFlg1;
                editText1.postInvalidate();
                //切换后将EdieText光标于末尾
                  charSequence=editText1.getText();
                if(charSequence instanceof Spannable){
                    Spannable spanText= (Spannable) charSequence;
                    Selection.setSelection(spanText,charSequence.length());
                }
                break;
            case R.id.btn2:

                //点击密码切换为可见可隐藏的状态
                if(!mbDisplayFlg2){
                    //设置EditText文本可见
                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //设置EditText文本为隐藏
                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg2=!mbDisplayFlg2;
                editText2.postInvalidate();
                //切换后将EdieText光标于末尾
                 charSequence=editText2.getText();
                if(charSequence instanceof Spannable){
                    Spannable spanText= (Spannable) charSequence;
                    Selection.setSelection(spanText,charSequence.length());
                }
                break;
            case R.id.btn3:
                //点击密码切换为可见可隐藏的状态
                if(!mbDisplayFlg3){
                    //设置EditText文本可见
                    editText3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //设置EditText文本为隐藏
                    editText3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg3=!mbDisplayFlg3;
                editText3.postInvalidate();
                //切换后将EdieText光标于末尾
                   charSequence=editText3.getText();
                if(charSequence instanceof Spannable){
                    Spannable spanText= (Spannable) charSequence;
                    Selection.setSelection(spanText,charSequence.length());
                }
                break;
            case R.id.btn4:
                if(!isValid())
                    break;
              getAlter();
             // getAlters();
                break;
        }
    }
    /**
     * 修改密码
     */
    private void   getAlter(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String login_url= Pub.SPOP_URI+"password/changePassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,login_url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("TAG", "response=" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String  state= (String) jsonObject.get("state");
                    String resultMessage= (String) jsonObject.get("resultMessage");
                    Log.e("AAAA","jsonObject="+jsonObject.toString());
                    Log.e("AAAAHQQQH", "sresultMessage=" + resultMessage.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(XiuGaiMiMaActivity.this,"修改密码失败",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> registerParams = new HashMap<>();
                registerParams.put("password",password);
                registerParams.put("newPassword", newPassword);
         /*      registerParams.put("jsession",Pub.mSESSION);
                Log.e("调试", "Pub.mSESSION=" + Pub.mSESSION);*/

                return   registerParams;
            }

         @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
             if(Pub.mSESSION==null && Pub.mSESSION.length()>0){
                 HashMap<String,String> headers=new HashMap<>();
                 headers.put("Accept", "application/json");
                 //headers.put("Content-Type","application/json; charset=UTF-8");
                 headers.put("Content-Type","application/x-www-form-urlencoded");
                 headers.put("cookie",Pub.mSESSION);
                 headers.put("jsession","Pub.mSESSION");
                 Log.e("调试", "headers----------------" + headers);
                 return headers;
             }else {
                 return super.getHeaders();
             }
            }

                 @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> superResponse = super .parseNetworkResponse(response);
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
               Pub.mSESSION = rawCookies.substring(0, rawCookies.indexOf(";"));
                Log.e("sessionid", "Pub.mSESSION----------------" + Pub.mSESSION);
                return superResponse;
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }

    public boolean isValid() {
        password=editText1.getText().toString().trim();
        newPassword=editText2.getText().toString().trim();
        newPassword1=editText3.getText().toString().trim();

        if (password.equals("")){
            Toast.makeText(this, "旧密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }/*else if(password.equals(password)){
            Toast.makeText(this, "旧密码错误!", Toast.LENGTH_SHORT).show();
            return false;
        }*/ if (newPassword.equals("")) {
            Toast.makeText(this, "新密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword.length() < 6 && newPassword.length()>16 ) {
            Toast.makeText(this, "新密码至少6位!", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (newPassword.equals(password)) {
            Toast.makeText(this, "新密码不能与旧密码一致", Toast.LENGTH_SHORT).show();
            return false;
        } */else if (!pswFilter(newPassword,Pub.PASSWORD_REGEX)) {
            Toast.makeText(this, "密码不能特殊字符", Toast.LENGTH_SHORT).show();
            return false;
        }  if (!newPassword1.equals(newPassword)) {
            Toast.makeText(this, "确认密码与密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
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
