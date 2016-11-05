package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
 * Created by Administrator on 2016/6/3.
 *
 */
public class ResetPasswordActivity extends Activity implements View.OnClickListener{
    private EditText newPassword,resetPassword;
    private Button button;
    private String password;
    private String password1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chongzhimima_main);
        initViews();
    }
    private void initViews() {
        newPassword= (EditText) findViewById(R.id.editText1);
        resetPassword= (EditText) findViewById(R.id.editText2);
        button= (Button) findViewById(R.id.btn);
        imageView= (ImageView) findViewById(R.id.image0);

        imageView.setOnClickListener(this);
        newPassword.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText1:
                break;
            case R.id.editText2:
                break;
            case R.id.btn:
                if(!isValid())
                    break;
               getReset();
                break;
            case R.id.image0:
              finish();
                break;
        }
    }
    private void  getReset(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url= Pub.SPOP_URI+"password/updatePassword";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
            url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String  state= (String) jsonObject.get("state");
                    String resultMessage= (String) jsonObject.get("resultMessage");
                    Log.e("AAAA","jsonObject="+jsonObject.toString());
                    Log.e("AAABBBBBA","state="+state.toString());
                    Log.e("AAAAHQQQH", "sresultMessage=" + resultMessage.toString());
                    if (Integer.parseInt(state)==0){
                        Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ResetPasswordActivity.this,"重置密码错误",Toast.LENGTH_SHORT).show();
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
                Class klass = volleyError.getClass();

                Toast.makeText(ResetPasswordActivity.this,"重置密码失败",Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>   registerParams = new HashMap<>();
                registerParams.put("password", password);
                return   registerParams;
            }
            //设置请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(ZhaoHuiMiMaActivityy.localCookie!=null &&ZhaoHuiMiMaActivityy.localCookie.length()>0){
                    HashMap<String,String> headers=new HashMap<>();
                    headers.put("cookie", ZhaoHuiMiMaActivityy.localCookie);
                    return headers;
                }else {
                    return super.getHeaders();
                }
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }

    public boolean isValid() {
        password=newPassword.getText().toString().trim();
        password1=resetPassword.getText().toString().trim();

        if (password.equals("")) {
            Toast.makeText(this, "新密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "新密码至少6位!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!pswFilter(password,Pub.PASSWORD_REGEX)) {
            Toast.makeText(this, "密码不能特殊字符", Toast.LENGTH_SHORT).show();
            return false;

        }  if (!password1.equals(password)) {
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
