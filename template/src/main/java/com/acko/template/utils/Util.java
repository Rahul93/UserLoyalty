package com.acko.template.utils;

import com.google.common.base.CaseFormat;

import java.security.SecureRandom;
import java.util.Random;

public class Util {
    public static String camelToUpperSnakeCase(String s) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, s);
    }

    public static String createRandomCode(int codeLength){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output ;
    }
}
