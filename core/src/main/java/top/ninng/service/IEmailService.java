package top.ninng.service;

import top.ninng.entity.UnifyResponse;

/**
 * @Author OhmLaw
 * @Date 2023/1/10 20:42
 * @Version 1.0
 */
public interface IEmailService {

    /**
     * 发送官方邮件
     *
     * @param title
     * @param content
     * @param addressee
     * @return
     */
    UnifyResponse<String> sendOfficialEmail(String title, String content, String addressee);
}
