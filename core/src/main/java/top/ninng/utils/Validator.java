package top.ninng.utils;

import java.util.regex.Pattern;

/**
 * 校验器
 *
 * @Author OhmLaw
 * @Date 2023/1/12 14:51
 * @Version 1.0
 */
public class Validator {

    /**
     * 邮箱正则表达式
     */
    public final static String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 检测是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
