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
        defaultCommentId = Long.parseLong(getConfig.map().get("DEFAULT_COMMENT_USER_ID"));
        System.out.println("commentId 1:" + idObfuscator.encode(1, IdConfig.COMMENT_ID));
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public UnifyResponse<String> comment(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("content") String content,
            @RequestParam("parentId") String parentId,
            @RequestParam("articleId") String articleId,
            HttpServletRequest request) {
        long[] realId;
        long[] realParentId;
        long[] realArticleId;
        if (StpUtil.isLogin()) {
            realId = new long[]{Long.parseLong((String) StpUtil.getLoginId())};
        } else {
            realId = new long[]{defaultCommentId};
        }
        if (!"-1".equals(parentId)) {
            realParentId = idObfuscator.decode(parentId, IdConfig.COMMENT_ID);
        } else {
            realParentId = new long[]{-1L};
        }
        realArticleId = idObfuscator.decode(articleId, IdConfig.ARTICLE_ID);
        if (realParentId.length > 0 && realArticleId.length > 0) {
            return iCommentService.comment(name, email, content, realArticleId[0], realId[0], realParentId[0],
                    Ip.getIpAddr(request));
        }
        return UnifyResponse.fail();
    }

    @RequestMapping(value = "/childById")
    public UnifyResponse<ArrayList<Comment>> getChildCommentsById(
            @RequestParam("id") String id) {
        long[] realId = idObfuscator.decode(id, IdConfig.COMMENT_ID);
        if (realId.length > 0) {
            return iCommentService.getChildCommentsById(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }

    @RequestMapping(value = "/byArticleId", method = RequestMethod.POST)
    public UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(
            @RequestParam("articleId") String articleId) {
        long[] realId = idObfuscator.decode(articleId, 1);
        if (realId.length > 0) {
            return iCommentService.getCommentByArticleId(realId[0]);
        }
        return UnifyResponse.fail("id 错误！", null);
    }

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
