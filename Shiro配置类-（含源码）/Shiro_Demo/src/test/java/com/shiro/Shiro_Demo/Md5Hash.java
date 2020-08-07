package com.shiro.Shiro_Demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class Md5Hash {

    @Test
    public void Md5Hash(){
        String hashName = "md5";
        String pwd = "123456";
        Object simpleHash = new SimpleHash(hashName, pwd, null, 3);
        System.out.println(simpleHash);
    }
}
