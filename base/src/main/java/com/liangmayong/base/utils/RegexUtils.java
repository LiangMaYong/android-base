package com.liangmayong.base.utils;

import java.util.regex.Pattern;

/**
 * Created by LiangMaYong on 2016/11/8.
 */

public class RegexUtils {

    /**
     * REGEX_MOBILE_SIMPLE
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * REGEX_MOBILE_EXACT
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    /**
     * REGEX_TEL
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * REGEX_IDCARD15
     */
    public static final String REGEX_IDCARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * REGEX_IDCARD18
     */
    public static final String REGEX_IDCARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * REGEX_EMAIL
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * REGEX_URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    /**
     * REGEX_CHZ
     */
    public static final String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";
    /**
     * REGEX_NON_SPECIAL
     */
    public static final String REGEX_NON_SPECIAL = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * REGEX_DATE
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * REGEX_IP
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * isMobileSimple
     *
     * @param string string
     * @return is or not
     */
    public static boolean isMobileSimple(String string) {
        return isMatch(REGEX_MOBILE_SIMPLE, string);
    }

    /**
     * isMobileExact
     *
     * @param string string
     * @return is or not
     */
    public static boolean isMobileExact(String string) {
        return isMatch(REGEX_MOBILE_EXACT, string);
    }

    /**
     * isTel
     *
     * @param string string
     * @return is or not
     */
    public static boolean isTel(String string) {
        return isMatch(REGEX_TEL, string);
    }

    /**
     * isIDCard15
     *
     * @param string string
     * @return is or not
     */
    public static boolean isIDCard15(String string) {
        return isMatch(REGEX_IDCARD15, string);
    }

    /**
     * isIDCard18
     *
     * @param string string
     * @return is or not
     */
    public static boolean isIDCard18(String string) {
        return isMatch(REGEX_IDCARD18, string);
    }

    /**
     * isEmail
     *
     * @param string string
     * @return is or not
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    /**
     * isURL
     *
     * @param string string
     * @return is or not
     */
    public static boolean isURL(String string) {
        return isMatch(REGEX_URL, string);
    }

    /**
     * isChz
     *
     * @param string string
     * @return is or not
     */
    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }


    /**
     * isNonSpecial
     *
     * @param string string
     * @return is or not
     */
    public static boolean isNonSpecial(String string) {
        return isMatch(REGEX_NON_SPECIAL, string);
    }

    /**
     * isDate
     *
     * @param string string
     * @return is or not
     */
    public static boolean isDate(String string) {
        return isMatch(REGEX_DATE, string);
    }

    /**
     * isIP
     *
     * @param string string
     * @return is or not
     */
    public static boolean isIP(String string) {
        return isMatch(REGEX_IP, string);
    }

    /**
     * isMatch
     *
     * @param string string
     * @return is or not
     */
    public static boolean isMatch(String regex, String string) {
        return !StringUtils.isEmpty(string) && Pattern.matches(regex, string);
    }
}
