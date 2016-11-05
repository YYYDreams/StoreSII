package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
 * Created by Administrator on 2016/5/23.
 */
public class ZhuCeActivity extends Activity implements View.OnClickListener {
    // 声明用到的页面控件
    private static ZhuCeActivity instance;
    private TextView textView;
    private ImageView imageView;
    private Button button1,button2;
    private EditText editText1,editText2,editText3,editText4,editText5,editText6;
    private CheckBox checkBox;
    // 定义变量
    private String userPhone;
    private String passWord;
    private String userName;
    private String passWord1;
    private String email;

    private boolean anniu=false;
    /**客户端输入的验证码*/
    private String valicationCode;
    //手机验证
    private String phoneUrl=Pub.SPOP_URI+"register/validatePhone";
    //用户名验证
    private String nameUrl=Pub.SPOP_URI+"register/validateUsername";
    //邮箱验证
    private String emailUrl=Pub.SPOP_URI+"register/validateEmail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce_mian);
        setViews();
        setLines();
    }


    private void setLines() {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
    }

    private void setViews() {

        textView= (TextView) findViewById(R.id.t_View0);
        imageView= (ImageView) findViewById(R.id.image);
        button1= (Button) findViewById(R.id.btn1);
        button2= (Button) findViewById(R.id.btnZhuce);
        editText1= (EditText) findViewById(R.id.editPhone);
        editText2= (EditText) findViewById(R.id.editName);
        editText3= (EditText) findViewById(R.id.editPassword);
        editText4= (EditText) findViewById(R.id.editPassword1);
        editText5= (EditText) findViewById(R.id.editEail);
        editText6= (EditText) findViewById(R.id.editYanma);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        //初始化监听事件
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);


        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    userPhone = editText1.getText().toString().trim();
                    if (userPhone.equals("")) {
                        Toast.makeText(ZhuCeActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                    }else if(!pswFilter(userPhone,Pub.PHONE_REGEX)){
                        Toast.makeText(ZhuCeActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    }
                    else
                    getRegisters(phoneUrl, "phone", userPhone);
                }
            }
        });
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    userName = editText2.getText().toString().trim();
                    if (userName.equals("")) {
                        Toast.makeText(ZhuCeActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();

                    }else if(!pswFilter(userName,Pub.NAME_REGEX)){
                        Toast.makeText(ZhuCeActivity.this, "用户名只能为数字、字母和下划线", Toast.LENGTH_SHORT).show();
                    }
                    else
                        getRegisters(nameUrl, "username", userName);

                }
            }

        });
        editText5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    email=editText5.getText().toString().trim();
                    if (email.equals("")) {
                        Toast.makeText(ZhuCeActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();

                    } else  if (!pswFilter(email,Pub.EMAL_REGEX)) {
                        Toast.makeText(ZhuCeActivity.this, "邮箱输入有误", Toast.LENGTH_SHORT).show();
                    }
                    else
                        getRegisters(emailUrl,"email",email);

                }
            }
        });

    }
    private  boolean isvalidate(){
        // 获取控件输入的值
        userPhone=editText1.getText().toString().trim();
        if(userPhone.equals("")){
            Toast.makeText(this,"手机号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!pswFilter(userPhone,Pub.PHONE_REGEX)){
            Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:  //获取验证码
                if(!isvalidate())
                break;
                getVerificationcode();
                break;
            case R.id.btnZhuce: //注册
                if (!isValid())
                    break;
                getRegister();
                break;
            case R.id.t_View0:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.image:
                ZhuCeActivity.this.finish();
            default:
                break;

        }
    }
    /**
     * 获得验证码
     */
    private void getVerificationcode(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Map<Object,Object> params=new HashMap<>();
        params.put("userPhone", userPhone);
        JSONObject jsonObject=new JSONObject(params);
        String url=Pub.SPOP_URI+"register/view";
        JsonRequest<JSONObject> jsonRequest=new JsonObjectRequest( Request.Method.POST
                ,url,jsonObject,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=(JSONObject)response.getJSONObject("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ZhuCeActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonRequest);

    }

    /**
     * 注册
     */
    private void  getRegisters(String url, final String phone , final String phonpe){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("TAG", "response="+response);
                    JSONObject jsonObject = new JSONObject(response);
                    String resule="注册成功";
                    Toast.makeText(ZhuCeActivity.this,response,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
                Toast.makeText(ZhuCeActivity.this,"注册失败",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>   registerParams = new HashMap<>();
                registerParams.put(phone, phonpe);                 //获取手机号
                return   registerParams;
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

    /**
     * 验证
     * POST的请求发送
     */
    private void  getRegister(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url= Pub.SPOP_URI+"register/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
               url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("TAG", "response="+response);
                    JSONObject jsonObject = new JSONObject(response);
                    String resule="注册成功";
                    Toast.makeText(ZhuCeActivity.this,response,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
                Toast.makeText(ZhuCeActivity.this,"注册失败",Toast.LENGTH_LONG).show();
            }
        }){
            /**
             * 发送参数
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>   registerParams = new HashMap<>();
                registerParams.put("phone", userPhone);                 //获取手机号
                registerParams.put("username", userName);                         //获取有户名
                registerParams.put("password", passWord);                        //获取密码
                registerParams.put("email", email);                             //获取邮箱
                registerParams.put("valicationCode", valicationCode);            //获取验证码
                return   registerParams;
            }
            /***
             * 请求头
             */

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

    /***
     * 说明：注册之前判断数据是否为空
     */

  public boolean isValid() {

       userPhone=editText1.getText().toString().trim();
        userName = editText2.getText().toString().trim();
        valicationCode = editText6.getText().toString().trim();
        passWord = editText3.getText().toString().trim();
        passWord1=editText4.getText().toString().trim();
        email=editText5.getText().toString().trim();
        anniu=checkBox.isChecked();

      if(userPhone.equals("")){
          Toast.makeText(this,"手机号不能为空",Toast.LENGTH_SHORT).show();
          return false;
      }
      else if(!pswFilter(userPhone,Pub.PHONE_REGEX)){
          Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
          return false;

      }

        if (userName.equals("")) {
            Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pswFilter(userName,Pub.NAME_REGEX)) {
            Toast.makeText(this, "用户名只能为数字、字母和下划线", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passWord.equals("")) {
            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passWord.length() < 6) {
            Toast.makeText(this, "密码至少6位!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!pswFilter(passWord,Pub.PASSWORD_REGEX)) {
            Toast.makeText(this, "密码不能特殊字符", Toast.LENGTH_SHORT).show();
            return false;

        }  if (!passWord1.equals(passWord)) {
          Toast.makeText(this, "确认密码与密码不一致", Toast.LENGTH_SHORT).show();
          return false;
      }
      if (!pswFilter(email, Pub.EMAL_REGEX)) {
          Toast.makeText(this, "邮箱输入有误", Toast.LENGTH_SHORT).show();
          return false;
      }  if (valicationCode.equals("")) {
        Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
        return false;
    }
      if (anniu==false) {
          Toast.makeText(this, "未同意用户协议!", Toast.LENGTH_SHORT).show();
          return false;
      }
        return true;
    }

    /***
     * 使用正则表达式方法
     * @param s
     * @param string
     * @return
     */
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



