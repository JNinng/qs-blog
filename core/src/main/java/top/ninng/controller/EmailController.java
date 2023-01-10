package top.ninng.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IEmailService;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;

/**
 * @Author OhmLaw
 * @Date 2023/1/10 19:49
 * @Version 1.0
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    IEmailService iEmailService;
    IdObfuscator idObfuscator;
    GetConfig getConfig;
    long rootId;

    public EmailController(IEmailService iEmailService, IdObfuscator idObfuscator, GetConfig getConfig) {
        this.iEmailService = iEmailService;
        this.idObfuscator = idObfuscator;
        this.getConfig = getConfig;
        this.rootId = Long.parseLong(getConfig.map().get("ROOT_USER"));
    }

    @RequestMapping(value = "/sendOfficial", method = RequestMethod.POST)
    public UnifyResponse<String> sendEmail(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "addressee") String addressee) {
        if (StpUtil.isLogin()) {
            long loginId = Long.parseLong((String) StpUtil.getLoginId());
            if (loginId == rootId) {
                return iEmailService.sendOfficialEmail(title, content, addressee);
            } else {
                return UnifyResponse.fail("权限不足！");
            }
        }
        return UnifyResponse.fail("您还未登录！");
    }
}
