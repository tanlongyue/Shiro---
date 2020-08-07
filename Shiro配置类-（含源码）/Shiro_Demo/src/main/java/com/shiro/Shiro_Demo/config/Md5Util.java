package com.shiro.Shiro_Demo.config;

import org.apache.shiro.crypto.hash.SimpleHash;

public  class  Md5Util {

    /**
     * 实现用户注册的时候 进行对密码的一个MD5的加密处理 并且规定加密的次数
     * */
    public static Object Md5PwdCodeUtils(String pwd){
        String hashName = "md5";
        Object simpleHash = new SimpleHash(hashName, pwd, null, 3);
        return String.valueOf(simpleHash);
    }


    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }
    public static void main(String[] args){
//            String name = "FGFBBB\u0017\u0010D\u0015\u0011\u0011\u0015LDDFM\u0017FC\u0011G\u0017DEALF";
//            name = convertMD5(name);
            System.out.println(Md5PwdCodeUtils("123456"));

        //bb232666cd0aeea80029c27e3c01582f
        //d93ae65992caf6a8751e334d0a716ad8
    }
}
