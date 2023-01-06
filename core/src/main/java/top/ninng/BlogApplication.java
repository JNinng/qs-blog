package top.ninng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author OhmLaw
 * @Date 2022/12/29 15:38
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("top.ninng.mapper")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
