package top.ninng.utils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 邮件工具
 *
 * @Author OhmLaw
 * @Date 2023/1/11 14:52
 * @Version 1.0
 */
public class Email {

    public static MimeMessage createMimeMessage(Session session,
                                                String personal, String email, String addresseePersonal,
                                                String addressee,
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

    /**
     * @param personal          发件人
     * @param email             发件人邮箱
     * @param password          发件邮箱授权密码
     * @param addresseePersonal 收件人
     * @param addressee         收件人邮箱
     * @param title             邮件标题
     * @param content           邮件正文
     * @param protocol          邮件协议（smtp）
     * @param host              邮件服务地址
     * @param port              邮件服务端口
     * @return 是否发送成功
     */
    public static boolean sendEmail(
            String personal, String email, String password,
            String addresseePersonal, String addressee,
            String title, String content,
            String protocol, String host, String port) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
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
}
