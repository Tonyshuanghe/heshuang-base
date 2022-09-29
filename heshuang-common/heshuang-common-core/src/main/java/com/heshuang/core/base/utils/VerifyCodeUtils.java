package com.heshuang.core.base.utils;


import java.util.Random;

/**
 * 生成验证码
 * @author heshuang
 */
public class VerifyCodeUtils {
  public static String randomCode(int cout) {
    StringBuilder str = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < cout; i++)
      str.append(random.nextInt(10)); 
    return str.toString();
  }
}
