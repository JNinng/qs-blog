package top.ninng.service;

import top.ninng.entity.Article;
import top.ninng.entity.ArticleIdListPageResult;
import top.ninng.entity.UnifyResponse;

/**
 * @Author OhmLaw
 * @Date 2023/1/6 11:34
 * @Version 1.0
 */
public interface IArticleService {

    /**
     * 根据 id 获取文章
     *
     * @param id
     * @return
     */
    UnifyResponse<Article> getArticleById(long id);

    /**
     * 根据页数获取文章 id
     *
     * @param page
     * @param pageSize
     * @return
     */
    UnifyResponse<ArticleIdListPageResult> getArticleIdListByPage(int page, int pageSize);

    /**
     * 根据 id 获取文章简略信息（无正文）
     *
     * @param id
     * @return
     */
    UnifyResponse<Article> getArticleInfoById(long id);

    /**
     * 根据 id 获取文章预览版
     *
     * @param id
     * @return
     */
    UnifyResponse<Article> getArticlePreviewById(long id);

    /**
     * 根据 id 更新文章
     *
     * @param id
     * @param userId
     * @param content
     * @param title
     * @param ip
     * @return
     */
    UnifyResponse<String> updateArticleById(long id, long userId, String content, String title, String ip);


    /**
     * 上传新文章
     *
     * @param id
     * @param content
     * @param title
     * @return
     */
    UnifyResponse<String> upload(long id, String content, String title);
}
