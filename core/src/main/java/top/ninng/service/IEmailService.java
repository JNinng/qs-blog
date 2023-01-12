package top.ninng.service;

import top.ninng.entity.UnifyResponse;

/**
 * 邮件服务接口
 *
 * @Author OhmLaw
 * @Date 2023/1/10 20:42
 * @Version 1.0
 */
public interface IEmailService {

    /**
     * 发送官方邮件
     *
     * @param title     标题
     * @param content   中文
     * @param addressee 收件人
     * @return 发送结果
     */
    UnifyResponse<String> sendOfficialEmail(String title, String content, String addressee);
}
