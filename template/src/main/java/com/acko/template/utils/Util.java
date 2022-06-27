package com.acko.template.utils;

import com.google.common.base.CaseFormat;

public class Util {
    public static String camelToUpperSnakeCase(String s) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, s);
    }
}
