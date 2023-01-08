package top.ninng.test;

import org.junit.Before;
import org.junit.Test;
import top.ninng.utils.IdObfuscator;

import java.util.Arrays;

/**
 * @Author OhmLaw
 * @Date 2022/12/31 15:32
 * @Version 1.0
 */
public class IdObfuscatorTest {

    public IdObfuscator idObfuscator;

    @Before
    public void init() {
        idObfuscator = new IdObfuscator();
        idObfuscator.init(new String[]{"test key"}, new int[]{8});
    }

    @Test
    public void test() {
        for (int i = 0; i < 4; i++) {
            String s = idObfuscator.encode(i, 0);
            long[] j = idObfuscator.decode(s, 0);
            System.out.println(s + " " + Arrays.toString(j));
        }
    }
}
