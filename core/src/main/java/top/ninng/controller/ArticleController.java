package top.ninng.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;
import top.ninng.entity.Article;
import top.ninng.entity.ArticleIdListPageResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.IArticleService;
import top.ninng.utils.IdObfuscator;
import top.ninng.utils.Ip;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author OhmLaw
 * @Date 2023/1/6 17:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    IArticleService iArticleService;
    IdObfuscator idObfuscator;

    public ArticleController(IArticleService iArticleService, IdObfuscator idObfuscator) {
        this.iArticleService = iArticleService;
        this.idObfuscator = idObfuscator;
        System.out.println(idObfuscator.encode(1, 1));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UnifyResponse<Article> getArticleById(
            @PathVariable(value = "id") String id) {
        long[] realId = idObfuscator.decode(id, 1);
        if (realId.length > 0) {
            return iArticleService.getArticleById(realId[0]);
        }
        return UnifyResponse.fail();
    }

    @RequestMapping(value = "/getIdListPage", method = RequestMethod.POST)
    public UnifyResponse<ArticleIdListPageResult> getArticleIdListByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "pageSize") int pageSize) {
        return iArticleService.getArticleIdListByPage(page, pageSize);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public UnifyResponse<Article> getArticleInfoById(
            @PathVariable(value = "id") String id) {
        long[] realId = idObfuscator.decode(id, 1);
        if (realId.length > 0) {
            return iArticleService.getArticleInfoById(realId[0]);
        }
        return UnifyResponse.fail();
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public UnifyResponse<Article> getArticlePreviewById(
            @PathVariable(value = "id") String id) {
        long[] realId = idObfuscator.decode(id, 1);
        if (realId.length > 0) {
            return iArticleService.getArticlePreviewById(realId[0]);
        }
        return UnifyResponse.fail();
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public UnifyResponse<String> updateArticleById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "title") String title,
            HttpServletRequest request) {
        if (StpUtil.isLogin()) {
            long[] realUserId = idObfuscator.decode(userId, 0);
            long[] realId = idObfuscator.decode(id, 1);
            if (realUserId.length > 0 && realId.length > 0) {
                long loginId = Long.parseLong((String) StpUtil.getLoginId());
                if (loginId == realUserId[0]) {
                    return iArticleService.updateArticleById(realId[0], realUserId[0], content, title,
                            Ip.getIpAddr(request));
                }
            }
            return UnifyResponse.fail("id 错误！");
        }
        return UnifyResponse.fail("您还未登录！");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public UnifyResponse<String> upload(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "title") String title) {
        if (StpUtil.isLogin()) {
            long loginId = Long.parseLong((String) StpUtil.getLoginId());
            return iArticleService.upload(loginId, content, title);
        }
        return UnifyResponse.fail("您还未登录！");
    }
}
