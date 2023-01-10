package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IEmailService;
import top.ninng.utils.GetConfig;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @Author OhmLaw
 * @Date 2023/1/10 20:44
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements IEmailService {

    GetConfig getConfig;
    String email;
    String password;
    String smtp;
    String port;
    String protocol;
    String personal;

    public EmailServiceImpl(GetConfig getConfig) {
        this.getConfig = getConfig;
        this.email = getConfig.map().get("EMAIL");
        this.password = getConfig.map().get("EMAIL_AUTHORIZATION_PASSWORD");
        this.smtp = getConfig.map().get("mail.smtp.host");
        this.port = getConfig.map().get("mail.smtp.port");
        this.protocol = getConfig.map().get("mail.transport.protocol");
        this.personal = getConfig.map().get("OFFICIAL_NAME");
    }

    public MimeMessage createMimeMessage(Session session,
                                         String personal, String email, String addresseePersonal, String addressee,
                                         String title, String content) {
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(email, personal, "UTF-8"));
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(addressee, addresseePersonal,
                    "UTF-8"));

            mimeMessage.setSubject(title, "UTF-8");
            mimeMessage.setContent(content, "text/html;charset=UTF-8");
            mimeMessage.saveChanges();

            return mimeMessage;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sendEmail(
            String email, String personal,
            String addresseePersonal, String addressee,
            String title, String content) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", smtp);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);

        MimeMessage mimeMessage = createMimeMessage(session,
                personal, email, addresseePersonal, addressee,
                title, content);

        try {
            Transport transport = session.getTransport();

            transport.connect(email, password);

            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendOfficial(String title, String content, String addressee) {
        return sendEmail(this.email, this.personal,
                "user", addressee,
                title, content);
    }

    @Override
    public UnifyResponse<String> sendOfficialEmail(String title, String content, String addressee) {
        return sendOfficial(title, content, addressee) ? UnifyResponse.ok("发送成功！") : UnifyResponse.fail("发送失败！");
    }
}
