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

    @Bean
    public IdObfuscator getIdObfuscator() {
        return new IdObfuscator().init("dev key", 8);
    }
}
