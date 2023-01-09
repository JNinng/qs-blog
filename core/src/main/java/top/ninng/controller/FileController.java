package top.ninng.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IImageService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Author OhmLaw
 * @Date 2023/1/8 20:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/image")
public class FileController {

    ServletContext servletContext;
    IImageService iImageService;

    public FileController(ServletContext servletContext, IImageService iImageService) {
        this.servletContext = servletContext;
        this.iImageService = iImageService;
    }

    @RequestMapping(value = "/{name}")
    public BufferedImage getImage(
            @PathVariable(value = "name") String name,
            HttpServletResponse response) {
        return iImageService.getImage(name, response);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public UnifyResponse<String> upload(
            @RequestParam(value = "files") MultipartFile[] files) {
        return iImageService.upload(files);
    }
}
