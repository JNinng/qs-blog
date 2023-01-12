package top.ninng.service;

import top.ninng.entity.Comment;
import top.ninng.entity.CommentResultItem;
import top.ninng.entity.UnifyResponse;

import java.util.ArrayList;

/**
 * @Author OhmLaw
 * @Date 2023/1/11 16:41
 * @Version 1.0
 */
public interface ICommentService {


    /**
     * 插入一条评论
     *
     * @param name      名称
     * @param email     邮件
     * @param content   中文
     * @param articleId 文章 id
     * @param userId    用户 id
     * @param parentId  父评论 id
     * @param ip        评论请求 ip 地址
     * @return 评论提交结果
     */
    UnifyResponse<String> comment(String name, String email, String content,
                                  Long articleId, Long userId, Long parentId, String ip);

    /**
     * 根据评论 id 获取子评论信息
     *
     * @param id 评论 id
     * @return 子评论列表
     */
    UnifyResponse<ArrayList<Comment>> getChildCommentsById(Long id);

    /**
     * 根据文章 id 获取评论
     *
     * @param articleId 文章 id
     * @return 文章下评论列表
     */
    UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(Long articleId);

    /**
     * 根据评论 id 获取评论信息
     *
     * @param id 评论 id
     * @return 评论信息
     */
    UnifyResponse<CommentResultItem> getCommentById(Long id);
}
