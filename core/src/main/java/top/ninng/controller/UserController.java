package top.ninng.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.entity.LoginResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IUserService;
import top.ninng.utils.IdObfuscator;

/**
 * @Author OhmLaw
 * @Date 2022/12/31 16:03
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    public IUserService iUserService;
    public IdObfuscator idObfuscator;

    public UserController(IUserService iUserService, IdObfuscator idObfuscator) {
        this.iUserService = iUserService;
        this.idObfuscator = idObfuscator;
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public UnifyResponse<String> checkLogin(
            @RequestParam(value = "id") String id) {
        if (StpUtil.isLogin()) {
            long[] resultId = idObfuscator.decode(id);
            if (resultId.length > 0) {
                return iUserService.checkLogin(resultId[0]);
            }
        }
        return UnifyResponse.fail("您还未登录！");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UnifyResponse<LoginResult> login(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password) {
        return iUserService.login(name, password);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public UnifyResponse<String> logout() {
        return iUserService.logout();
    }
}
