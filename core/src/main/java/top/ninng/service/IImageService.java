package top.ninng.service;

import org.springframework.web.multipart.MultipartFile;
import top.ninng.entity.UnifyResponse;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Author OhmLaw
 * @Date 2023/1/9 14:29
 * @Version 1.0
 */
public interface IImageService {

    /**
     * 获取 image
     *
     * @param name
     * @return
     */
    BufferedImage getImage(String name, HttpServletResponse response);

    /**
     * 上传 image
     *
     * @param files
     * @return
     */
    UnifyResponse<String> upload(MultipartFile[] files);
}
