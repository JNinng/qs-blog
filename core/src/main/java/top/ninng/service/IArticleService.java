package top.ninng.service;

import top.ninng.entity.Article;
import top.ninng.entity.ArticleIdListPageResult;
import top.ninng.entity.UnifyResponse;

/**
 * 文章服务接口
 *
 * @Author OhmLaw
 * @Date 2023/1/6 11:34
 * @Version 1.0
 */
public interface IArticleService {

    /**
     * 根据 id 获取文章
     *
     * @param id 文章 id
     * @return 文章信息
     */
    UnifyResponse<Article> getArticleById(long id);

    /**
     * 根据页数获取文章 id
     *
     * @param page     页数
     * @param pageSize 页大小
     * @return 指定页文章 id
     */
    UnifyResponse<ArticleIdListPageResult> getArticleIdListByPage(int page, int pageSize);

    /**
     * 根据 id 获取文章简略信息（无正文）
     *
     * @param id 文章 id
     * @return 指定文章简略信息
     */
    UnifyResponse<Article> getArticleInfoById(long id);

    /**
     * 根据 id 获取文章预览版
     *
     * @param id 文章 id
     * @return 指定文章预览版
     */
    UnifyResponse<Article> getArticlePreviewById(long id);

    /**
     * 根据 id 更新文章
     *
     * @param id      文章 id
     * @param userId  当前用户 id
     * @param content 正文
     * @param title   标题
     * @param ip      更新请求地 ip 地址
     * @return 更新结果
     */
    UnifyResponse<String> updateArticleById(long id, long userId, String content, String title, String ip);


    /**
     * 上传新文章
     *
     * @param id      用户 id
     * @param content 正文
     * @param title   标题
     * @return 上传结果
     */
    UnifyResponse<String> upload(long id, String content, String title);
}
