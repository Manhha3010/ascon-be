package com.exsoft.momedumerchant.util;

public class StringUtils extends org.springframework.util.StringUtils {

    public static final String REGEX_PHONE = "(\\+84|84|0[3|5|7|8|9])+([0-9]{8})\\b";

    public static String replacePhoneNumber(String phoneNumber) {
        if (hasText(phoneNumber)) {
            if (phoneNumber.startsWith("+84")) {
                phoneNumber = phoneNumber.replaceFirst("\\+84", "0");
            } else if (phoneNumber.startsWith("84")) {
                phoneNumber =  phoneNumber.replaceFirst("84","0");
            }
        }
        return phoneNumber;
    }

}
