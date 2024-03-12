package top.ninng.service;

import top.ninng.entity.AboutResult;
import top.ninng.entity.UnifyResponse;

/**
 * “关于”服务接口
 *
 * @Author OhmLaw
 * @Date 2023/1/11 15:59
 * @Version 1.0
 */
public interface IAboutService {

    /**
     * 获取网站关于的信息
     *
     * @return 关于信息
     */
    UnifyResponse<AboutResult> getInfo();
}
