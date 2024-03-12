package top.ninng.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.config.IdConfig;
import top.ninng.entity.ArticleIdListPageResult;
import top.ninng.entity.Tag;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.ITagService;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;

import java.util.ArrayList;

/**
 * 标签控制器
 *
 * @Author OhmLaw
 * @Date 2023/1/14 13:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    ITagService iTagService;
    IdObfuscator idObfuscator;
    GetConfig getConfig;
    long rootId;

    public TagController(ITagService iTagService, GetConfig getConfig, IdObfuscator idObfuscator) {
        this.iTagService = iTagService;
        this.getConfig = getConfig;
        this.idObfuscator = idObfuscator;
        // 从数据库获取 root 用户 id
        this.rootId = Long.parseLong(getConfig.map().get("ROOT_USER"));
    }

    /**
     * 添加新标签
     *
     * @param tagName 标签名
     * @return 添加结果
     */
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    public UnifyResponse<String> addTag(
            @RequestParam("name") String tagName) {
        return iTagService.addTag(tagName);
    }

    @RequestMapping(value = "/deleteTagByName", method = RequestMethod.POST)
    public UnifyResponse<String> deleteByName(
            @RequestParam("name") String tagName) {
        if (StpUtil.isLogin()) {
            long loginId = Long.parseLong((String) StpUtil.getLoginId());
            if (loginId == rootId) {
                return iTagService.deleteTagByName(tagName);
            }
        }
        return UnifyResponse.fail("您还未登录！");
    }

    /**
     * 根据 id 删除标签
     *
     * @param id 标签混淆 id
     * @return 删除结果
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public UnifyResponse<String> deleteTagById(
            @RequestParam("id") String id) {
        if (StpUtil.isLogin()) {
            long loginId = Long.parseLong((String) StpUtil.getLoginId());
            if (loginId == rootId) {
                long[] realId = idObfuscator.decode(id, IdConfig.TAG_ID);
                if (realId.length > 0) {
                    return iTagService.deleteTagById(realId[0]);
                }
            }
        }
        return UnifyResponse.fail("您还未登录！");
    }

    /**
     * 获取全部标签
     *
     * @return 全部标签
     */
    @RequestMapping(value = "/allTag", method = RequestMethod.POST)
    public UnifyResponse<ArrayList<Tag>> getAllTag() {
        return iTagService.getAllTag();
    }

    @RequestMapping(value = "/getIdListPageByName", method = RequestMethod.POST)
    public UnifyResponse<ArticleIdListPageResult> getArticleIdListPageByName(
            @RequestParam("name") String tagName,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {
        return iTagService.getArticleIdListPageByName(tagName, page, pageSize);
    }

    /**
     * 根据 id 获取标签
     *
     * @param id 混淆 id
     * @return 标签
     */
    @RequestMapping(value = "/id", method = RequestMethod.POST)
    public UnifyResponse<Tag> getTagById(
            @RequestParam("id") String id) {
        long[] realId = idObfuscator.decode(id, IdConfig.TAG_ID);
        if (realId.length > 0) {
            return iTagService.getTagById(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }

    /**
     * 根据 id 更新标签
     *
     * @param id   混淆 id
     * @param name 新标签名
     * @return 更新结果
     */
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public UnifyResponse<String> updateTagById(
            @RequestParam("id") String id,
            @RequestParam("name") String name) {
        if (StpUtil.isLogin()) {
            long loginId = Long.parseLong((String) StpUtil.getLoginId());
            if (loginId == rootId) {
                long[] realId = idObfuscator.decode(id, IdConfig.TAG_ID);
                if (realId.length > 0) {
                    return iTagService.updateTagById(realId[0], name);
                }
                return UnifyResponse.fail("id 错误！");
            }
        }
        return UnifyResponse.fail("您还未登录！");
    }
}
