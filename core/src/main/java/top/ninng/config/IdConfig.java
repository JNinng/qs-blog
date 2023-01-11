package top.ninng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ninng.utils.IdObfuscator;

/**
 * @Author OhmLaw
 * @Date 2023/1/2 17:27
 * @Version 1.0
 */
@Configuration
public class IdConfig {

    public static int USER_ID = 0;
    public static int ARTICLE_ID = 1;
    public static int COMMENT_ID = 2;

    @Bean
    public IdObfuscator getIdObfuscator() {
        return new IdObfuscator().init(
                new String[]{"dev userId key", "dev articleId key", "dev commentId key"},
                new int[]{8, 12, 12});
    }
}
