package top.ninng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.entity.AboutResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IAboutService;

/**
 * 关于控制器
 *
 * @Author OhmLaw
 * @Date 2023/1/11 15:56
 * @Version 1.0
 */
@RestController
@RequestMapping("/about")
public class AboutController {

    IAboutService iAboutService;

    public AboutController(IAboutService iAboutService) {
        this.iAboutService = iAboutService;
    }

    /**
     * 获取网“关于”信息
     *
     * @return 关于信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UnifyResponse<AboutResult> getInfo() {
        return iAboutService.getInfo();
    }
}
