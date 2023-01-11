package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.entity.AboutResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IAboutService;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.GetConfig;

/**
 * @Author OhmLaw
 * @Date 2023/1/11 16:00
 * @Version 1.0
 */
@Service
public class AboutServiceImpl implements IAboutService {

    AboutResult aboutResult;
    GetConfig getConfig;

    public AboutServiceImpl(GetConfig getConfig) {
        this.getConfig = getConfig;
        aboutResult = new AboutResult(
                getConfig.map().get("INFO"),
                getConfig.map().get("EMAIL"),
                getConfig.map().get("HEAD_PORTRAIT"));
    }

    @Override
    public UnifyResponse<AboutResult> getInfo() {
        if (EmptyCheck.notEmpty(aboutResult)) {
            return UnifyResponse.ok(aboutResult);
        }
        return UnifyResponse.fail();
    }
}
