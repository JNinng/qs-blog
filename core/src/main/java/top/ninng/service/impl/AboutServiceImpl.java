package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.config.IdConfig;
import top.ninng.entity.AboutResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.mapper.ArticleMapper;
import top.ninng.service.IAboutService;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;

/**
 * “关于”服务实现类
 *
 * @Author OhmLaw
 * @Date 2023/1/11 16:00
 * @Version 1.0
 */
@Service
public class AboutServiceImpl implements IAboutService {

    AboutResult aboutResult;
    GetConfig getConfig;
    ArticleMapper articleMapper;
    IdObfuscator idObfuscator;

    public AboutServiceImpl(GetConfig getConfig, ArticleMapper articleMapper, IdObfuscator idObfuscator) {
        this.getConfig = getConfig;
        this.idObfuscator = idObfuscator;
        this.articleMapper = articleMapper;
        // 从数据库获取“关于”信息
        aboutResult = new AboutResult(
                articleMapper.selectByPrimaryKey(Long.valueOf(getConfig.map().get("ABOUT_INFO_ARTICLE_ID"))).getContent(),
                getConfig.map().get("EMAIL"),
                getConfig.map().get("HEAD_PORTRAIT"),
                idObfuscator.encode(Integer.parseInt(getConfig.map().get("ABOUT_INFO_ARTICLE_ID")),
                        IdConfig.ARTICLE_ID));
    }

    /**
     * 获取关于信息
     *
     * @return 关于信息
     */
    @Override
    public UnifyResponse<AboutResult> getInfo() {
        loadAbout();
        if (EmptyCheck.notEmpty(aboutResult)) {
            return UnifyResponse.ok(aboutResult);
        }
        return UnifyResponse.fail();
    }

    public void loadAbout() {
        aboutResult = new AboutResult(
                articleMapper.selectByPrimaryKey(Long.valueOf(getConfig.map().get("ABOUT_INFO_ARTICLE_ID"))).getContent(),
                getConfig.map().get("EMAIL"),
                getConfig.map().get("HEAD_PORTRAIT"),
                idObfuscator.encode(Integer.parseInt(getConfig.map().get("ABOUT_INFO_ARTICLE_ID")),
                        IdConfig.ARTICLE_ID));
    }
}
