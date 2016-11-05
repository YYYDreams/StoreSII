package com.example.administrator.store3.util;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Pub {
    public static String mSESSION="";
    //我的设备ID
    public static  String mMyREGID="";

   public static  final String SPOP_URI="http://192.168.2.100:8080/wap/";
    public static  final String SPOP_URIS="http://192.168.2.100:8080";
    //public static  final String SPOP_URI="192.168.2.9:8090/ssmmf/";
    public static  String SESSIONID="mSESSIONID";
    //手机的正则表达式
    public static final String PHONE_REGEX = "((^(13|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
    //密码的正则表达式
    public static final String PASSWORD_REGEX = "[A-Z0-9a-z!@#$%^&*.~/\\{\\}|()'\"?><,.`\\+-=_\\[\\]:;]+";
    //用户名的正则表达式
    public static final String NAME_REGEX = "[A-Z0-9a-z_]+";
    //邮箱的正则表达式
    public static final String EMAL_REGEX="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*" ;

    //接口 登录与修改密码
    //注册用户名
    private static final String USERNAME="username";
    //注册密码
    private static final String PASSWORD="password";
    //注册手机号
    private static final String PHONE="phone";
    //注册邮箱
    private static final String EMAIL="email";
    //注册验证码
    private static final String VALICATIONCODE="valicationCode";





}
