package top.ninng.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import top.ninng.entity.LoginResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.entity.User;
import top.ninng.mapper.UserMapper;
import top.ninng.service.IUserService;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.IdObfuscator;

/**
 * @Author OhmLaw
 * @Date 2023/1/2 16:37
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    UserMapper userMapper;
    IdObfuscator idObfuscator;

    public UserServiceImpl(UserMapper userMapper, IdObfuscator idObfuscator) {
        this.userMapper = userMapper;
        this.idObfuscator = idObfuscator;
    }

    @Override
    public UnifyResponse<String> checkLogin(long id) {
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(id);
        Object result = StpUtil.getLoginIdByToken(tokenValueByLoginId);
        System.out.println(result);
        if (EmptyCheck.notEmpty(result)) {
            long resultId = Long.parseLong((String) result);
            if (resultId == id) {
                return UnifyResponse.ok("已登录！");
            }
        }
        return UnifyResponse.ok(id + " 未登录！");
    }

    @Override
    public UnifyResponse<LoginResult> login(String name, String password) {
        User selectUser = userMapper.selectByName(name);
        if (EmptyCheck.isEmpty(selectUser)) {
            return UnifyResponse.fail("账号不存在！", null);
        }
        if (password.equals(selectUser.getUserPassword())) {
            StpUtil.login(selectUser.getId());
            return UnifyResponse.ok("登录成功！", new LoginResult(idObfuscator.encode(selectUser.getId(), 0),
                    StpUtil.getTokenValue()));
        }
        return UnifyResponse.fail("账号或密码错误！", null);
    }

    @Override
    public UnifyResponse<String> logout() {
        StpUtil.logout();
        return UnifyResponse.ok("已登出！");
    }
}
