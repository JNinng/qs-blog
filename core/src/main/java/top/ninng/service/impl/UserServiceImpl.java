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
 * 用户服务实现类
 *
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

    /**
     * 检查登录状态
     *
     * @param id 目标用户 id
     * @return 登录状态
     */
    @Override
    public UnifyResponse<String> checkLogin(long id) {
        // TODO 登录状态逻辑
        // 根据 id 获取目标 token
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

    /**
     * 用户登录
     *
     * @param name     名称
     * @param password 密码
     * @return 登录结果
     */
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

    /**
     * 登出
     *
     * @return 登出结果
     */
    @Override
    public UnifyResponse<String> logout() {
        StpUtil.logout();
        return UnifyResponse.ok("已登出！");
    }
}
