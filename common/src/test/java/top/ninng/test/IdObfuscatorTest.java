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

    @Test
    public void idObfuscatorTest() {
        for (int i = 0; i < 4; i++) {
            String s = idObfuscator.encode(i, 0);
            long[] j = idObfuscator.decode(s, 0);
            String s1 = idObfuscator.encode(i, 1);
            long[] j1 = idObfuscator.decode(s1, 1);
            System.out.println(s + " " + Arrays.toString(j));
            System.out.println(s1 + " " + Arrays.toString(j1));
            System.out.println();
        }
    }

    @Before
    public void init() {
        idObfuscator = new IdObfuscator();
        idObfuscator.init(new String[]{"test key", "test1 key"}, new int[]{8, 12});
    }
}
