package com.example.administrator.store3.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.store3.util.Pub;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信登录必须要用到的类
 * Created by Administrator on 2016/8/12.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private  BaseResp resp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api= WXAPIFactory.createWXAPI(this, Pub.WEIXIN_KEY,false);
        api.handleIntent(getIntent(), this);
    }

    //微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }
    //第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        switch (resp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this,"发送取消",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this,"发送被拒绝",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                Toast.makeText(this,"发送返回",Toast.LENGTH_SHORT).show();
                finish();
                break;

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }
}























