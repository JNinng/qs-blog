package top.ninng.utils;

import org.hashids.Hashids;

/**
 * id混淆
 *
 * @Author OhmLaw
 * @Date 2022/12/31 15:18
 * @Version 1.0
 */
public class IdObfuscator {

    public String key = "my key";
    public int length = 8;
    public Hashids hashids;

    public long[] decode(String t) {
        return hashids.decode(t);
    }

    public String encode(int id) {
        return hashids.encode(id);
    }

    public IdObfuscator init(String key, int length) {
        this.key = key;
        this.length = length;
        hashids = new Hashids(key, length);
        return this;
    }
}
