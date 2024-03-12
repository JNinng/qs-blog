package top.ninng.test;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Author OhmLaw
 * @Date 2023/1/7 14:45
 * @Version 1.0
 */
public class ArticlePreviewTest {

    @Test
    public void arrayTest() {
        ArrayList<Long> longArrayList = new ArrayList<Long>();
        longArrayList.add(1L);
        longArrayList.add(2L);
        longArrayList.add(3L);
        longArrayList.add(4L);
        ArrayList<String> stringArrayList =
                longArrayList.stream().map(Object::toString).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(stringArrayList.get(0).getClass());
    }

    /**
     * 预览版信息截取测试
     */
    @Test
    public void previewContentTest() {
        String content = "sdfffsdf \n<!-- more -->\nsdgsgdfgdfhdf";
        String content1 = "sdfffsdfsdgsgdfgdfhdf";
        System.out.println(content.split("<!-- more -->\n")[0]);
        System.out.println(content1.indexOf("<!-- more -->"));
        System.out.println(content1.split("<!-- more -->\n")[0]);
    }

    /**
     * 数据库指定时间格式测试
     */
    @Test
    public void timeTest() {
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //        System.out.println(simpleDateFormat.format(new Date()));
        //        System.out.println(new Date());
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
