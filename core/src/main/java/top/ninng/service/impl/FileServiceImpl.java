package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IFileService;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.Path;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author OhmLaw
 * @Date 2023/1/9 14:30
 * @Version 1.0
 */
@Service
public class FileServiceImpl implements IFileService {

    String imgPath = Path.getWorkAbsolutePath() + "/data/img/";
    Pattern pattern = Pattern.compile("[^.]*$");
    String defaultName = "问号-1caa408d-3203-4a84-8b28-ea254c94a2be.png";
    String defaultImagePath = imgPath + defaultName;

    @Override
    public BufferedImage getImage(String name, HttpServletResponse response) {
        File imageFile = new File(imgPath + name);
        if (!imageFile.exists()) {
            imageFile = new File(defaultImagePath);
            name = defaultName;
        }
        OutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imageFile);
            BufferedImage image = ImageIO.read(inputStream);
            outputStream = response.getOutputStream();

            Matcher matcher = pattern.matcher(name);
            if (matcher.find()) {
                ImageIO.write(image, matcher.group(), outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (EmptyCheck.notEmpty(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (EmptyCheck.notEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public UnifyResponse<String> upload(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (EmptyCheck.isEmpty(file) || file.isEmpty()) {
                return UnifyResponse.fail("上传文件为空！");
            }
            String suffix =
                    Objects.requireNonNull(file.getOriginalFilename())
                            .substring(file.getOriginalFilename().lastIndexOf("."));
            String name = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(0, file.getOriginalFilename().lastIndexOf("."));
            String newFileName = name + "-" + UUID.randomUUID() + suffix;
            File fileTemp = new File(imgPath + newFileName);
            try {
                file.transferTo(fileTemp);
            } catch (IOException e) {
                e.printStackTrace();
                return UnifyResponse.fail("上传失败！");
            }
        }
        return UnifyResponse.ok("上传成功！");
    }
}
