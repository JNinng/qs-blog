package top.ninng.utils;

/**
 * @Author OhmLaw
 * @Date 2023/1/8 18:30
 * @Version 1.0
 */
public class Path {

    /**
     * 获取系统工作绝对路径
     *
     * @return
     */
    public static String getWorkAbsolutePath() {
        return "file:///" + System.getProperty("user.dir")
                .replaceAll("\\\\[^\\\\]*\\\\[^\\\\]*\\\\$", "")
                .replaceAll("\\\\", "/");
    }
}
