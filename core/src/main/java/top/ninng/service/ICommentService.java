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
     * @param name
     * @param email
     * @param content
     * @param userId
     * @return
     */
    UnifyResponse<String> comment(String name, String email, String content,
                                  Long articleId, Long userId, Long parentId, String ip);

    /**
     * 根据评论 id 获取子评论
     *
     * @param id
     * @return
     */
    UnifyResponse<ArrayList<Comment>> getChildCommentsById(Long id);

    /**
     * 根据文章 id 获取评论
     *
     * @param articleId
     * @return
     */
    UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(Long articleId);

    /**
     * 根据评论 id 获取评论
     *
     * @param id
     * @return
     */
    UnifyResponse<CommentResultItem> getCommentById(Long id);
}
