package top.ninng.test;

import org.junit.jupiter.api.Test;
import top.ninng.utils.Validator;

/**
 * @Author OhmLaw
 * @Date 2023/1/12 14:47
 * @Version 1.0
 */
public class ValidatorUtilTest {

    /**
     * 邮箱校验测试
     */
    @Test
    public void emailTest() {
        System.out.println(Validator.isEmail("a.@163.com"));
        System.out.println(Validator.isEmail("a@163.com"));
    }
}
