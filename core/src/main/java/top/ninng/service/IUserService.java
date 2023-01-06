package top.ninng.service;

import top.ninng.entity.LoginResult;
import top.ninng.entity.UnifyResponse;

/**
 * @Author OhmLaw
 * @Date 2023/1/2 16:28
 * @Version 1.0
 */
public interface IUserService {

    /**
     * 用户是否登录
     *
     * @param id
     * @return
     */
    UnifyResponse<String> checkLogin(long id);

    /**
     * 用户登录
     *
     * @param name
     * @param password
     * @return
     */
    UnifyResponse<LoginResult> login(String name, String password);

    /**
     * 用户登出
     *
     * @return
     */
    UnifyResponse<String> logout();
}
