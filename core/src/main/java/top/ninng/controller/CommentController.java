package top.ninng.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ninng.config.IdConfig;
import top.ninng.entity.Comment;
import top.ninng.entity.CommentResultItem;
import top.ninng.entity.UnifyResponse;
import top.ninng.service.ICommentService;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;
import top.ninng.utils.Ip;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 评论控制器
 *
 * @Author OhmLaw
 * @Date 2023/1/11 16:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    ICommentService iCommentService;
    IdObfuscator idObfuscator;
    GetConfig getConfig;
    long defaultCommentId;

    public CommentController(ICommentService iCommentService, IdObfuscator idObfuscator, GetConfig getConfig) {
        this.iCommentService = iCommentService;
        this.idObfuscator = idObfuscator;
        this.getConfig = getConfig;
        // 从数据库中读取游客用户默认 id
        defaultCommentId = Long.parseLong(getConfig.map().get("DEFAULT_COMMENT_USER_ID"));
        // 输出 commentId = 1 的混淆 id 用于测试
        System.out.println("commentId 1:" + idObfuscator.encode(1, IdConfig.COMMENT_ID));
    }

    /**
     * 评论
     *
     * @param name      名称
     * @param email     邮箱
     * @param content   正文
     * @param parentId  父评论
     * @param articleId 文章 id
     * @param request   用于获取 ip
     * @return 评论提交结果
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public UnifyResponse<String> comment(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("content") String content,
            @RequestParam("parentId") String parentId,
            @RequestParam("articleId") String articleId,
            HttpServletRequest request) {
        // 获取真实 id
        long[] realId;
        long[] realParentId;
        long[] realArticleId;

        if (StpUtil.isLogin()) {
            // 获取已登录用户 id
            realId = new long[]{Long.parseLong((String) StpUtil.getLoginId())};
        } else {
            // 游客用户使用默认 id
            realId = new long[]{defaultCommentId};
        }
        if (!"-1".equals(parentId)) {
            // 存在父评论时，解码获取父评论真实 id
            realParentId = idObfuscator.decode(parentId, IdConfig.COMMENT_ID);
        } else {
            // 无父评论默认 id 为 -1
            realParentId = new long[]{-1L};
        }
        realArticleId = idObfuscator.decode(articleId, IdConfig.ARTICLE_ID);
        if (realParentId.length > 0 && realArticleId.length > 0) {
            return iCommentService.comment(name, email, content, realArticleId[0], realId[0], realParentId[0],
                    Ip.getIpAddr(request));
        }
        return UnifyResponse.fail();
    }

    /**
     * 根据混淆评论 id 获取子评论
     *
     * @param id 混淆 id
     * @return 子评论列表
     */
    @RequestMapping(value = "/childById")
    public UnifyResponse<ArrayList<Comment>> getChildCommentsById(
            @RequestParam("id") String id) {
        long[] realId = idObfuscator.decode(id, IdConfig.COMMENT_ID);
        if (realId.length > 0) {
            return iCommentService.getChildCommentsById(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }

    /**
     * 根据混淆文章 id 获取评论
     *
     * @param articleId 混淆 id
     * @return 指定文章下的两级评论信息列表
     */
    @RequestMapping(value = "/byArticleId", method = RequestMethod.POST)
    public UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(
            @RequestParam("articleId") String articleId) {
        long[] realId = idObfuscator.decode(articleId, 1);
        if (realId.length > 0) {
            return iCommentService.getCommentByArticleId(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }

    /**
     * 根据混淆评论 id 获取评论信息
     *
     * @param id 混淆 id
     * @return 指定评论
     */
    @RequestMapping(value = "/byId", method = RequestMethod.POST)
    public UnifyResponse<CommentResultItem> getCommentById(
            @RequestParam("id") String id) {
        long[] realId = idObfuscator.decode(id, IdConfig.COMMENT_ID);
        if (realId.length > 0) {
            return iCommentService.getCommentById(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }
}
