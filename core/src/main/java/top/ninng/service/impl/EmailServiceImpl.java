package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IEmailService;
import top.ninng.utils.Email;
import top.ninng.utils.GetConfig;

/**
 * 邮件服务实现类
 *
 * @Author OhmLaw
 * @Date 2023/1/10 20:44
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements IEmailService {

    GetConfig getConfig;
    String email;
    String password;
    String host;
    String port;
    String protocol;
    String personal;

    public EmailServiceImpl(GetConfig getConfig) {
        this.getConfig = getConfig;
        // 从数据库获取邮件发送相关配置
        this.email = getConfig.map().get("EMAIL");
        this.password = getConfig.map().get("EMAIL_AUTHORIZATION_PASSWORD");
        this.host = getConfig.map().get("mail.smtp.host");
        this.port = getConfig.map().get("mail.smtp.port");
        this.protocol = getConfig.map().get("mail.transport.protocol");
        this.personal = getConfig.map().get("OFFICIAL_NAME");
    }

    /**
     * 发送官方邮件
     *
     * @param title     标题
     * @param content   正文
     * @param addressee 收件人
     * @return 发送结果
     */
    public boolean sendOfficial(String title, String content, String addressee) {
        return Email.sendEmail(
                this.personal, this.email, this.password,
                "user", addressee,
                title, content,
                this.protocol, this.host, this.port);
    }

    /**
     * 发送官方邮件
     *
     * @param title     标题
     * @param content   中文
     * @param addressee 收件人
     * @return 发送结果
     */
    @Override
    public UnifyResponse<String> sendOfficialEmail(String title, String content, String addressee) {
        return sendOfficial(title, content, addressee) ? UnifyResponse.ok("发送成功！") : UnifyResponse.fail("发送失败！");
    }
}
