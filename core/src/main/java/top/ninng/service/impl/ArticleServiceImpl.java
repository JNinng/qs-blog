package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.config.IdConfig;
import top.ninng.entity.Article;
import top.ninng.entity.ArticleIdListPageResult;
import top.ninng.entity.UnifyResponse;
import top.ninng.mapper.ArticleMapper;
import top.ninng.service.IArticleService;
import top.ninng.utils.IdObfuscator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 文章服务实现类
 *
 * @Author OhmLaw
 * @Date 2023/1/6 17:25
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements IArticleService {

    ArticleMapper articleMapper;
    IdObfuscator idObfuscator;

    public ArticleServiceImpl(ArticleMapper articleMapper, IdObfuscator idObfuscator) {
        this.articleMapper = articleMapper;
        this.idObfuscator = idObfuscator;
    }

    /**
     * 获取指定 id 的文章
     *
     * @param id 文章 id
     * @return 文章信息
     */
    @Override
    public UnifyResponse<Article> getArticleById(long id) {
        // 询持久层数据
        Article article = articleMapper.selectByPrimaryKey(id);
        // id 混淆处理
        article.setObfuscatorId(idObfuscator.encode(article.getId(), IdConfig.ARTICLE_ID));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), IdConfig.ARTICLE_ID));
        return UnifyResponse.ok(article);
    }

    /**
     * 分页查询 id
     *
     * @param page     页数
     * @param pageSize 页大小
     * @return 分页结果 id 列表
     */
    @Override
    public UnifyResponse<ArticleIdListPageResult> getArticleIdListByPage(int page, int pageSize) {
        // 处理网页分页逻辑和数据库分页查询逻辑
        page = (page <= 0) ? 1 : page;
        ArrayList<String> articleIdList =
                articleMapper.selectArticleIdListByPage((page - 1) * pageSize, pageSize)
                        //查询结果处理
                        .stream()
                        // id 混淆
                        .map(aLong -> idObfuscator.encode(Math.toIntExact(aLong), 1))
                        // 转化为列表
                        .collect(Collectors.toCollection(ArrayList::new));
        return UnifyResponse.ok(new ArticleIdListPageResult(articleIdList, page, pageSize));
    }

    /**
     * 获取指定 id 的文章简略信息
     *
     * @param id 文章 id
     * @return 文章信息
     */
    @Override
    public UnifyResponse<Article> getArticleInfoById(long id) {
        // 查询持久层数据
        Article article = articleMapper.selectByPrimaryKey(id);
        // id 混淆
        article.setObfuscatorId(idObfuscator.encode(article.getId(), 1));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), 1));
        // 简略处理
        article.setContent("");
        return UnifyResponse.ok(article);
    }

    /**
     * 获取指定 id 的预览版文章
     *
     * @param id 文章 id
     * @return 预览版文章
     */
    @Override
    public UnifyResponse<Article> getArticlePreviewById(long id) {
        // 查询持久层数据
        Article article = articleMapper.selectByPrimaryKey(id);
        // id 混淆
        article.setObfuscatorId(idObfuscator.encode(article.getId(), 1));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), 1));
        // 文章信息预览处理
        String content = article.getContent();
        if (content.indexOf("<!-- more -->") > 0) {
            article.setContent(content.split("<!-- more -->\n")[0]);
        } else {
            article.setContent("");
        }
        return UnifyResponse.ok(article);
    }

    /**
     * 根据指定 id 更新文章
     *
     * @param id      文章 id
     * @param userId  当前用户 id
     * @param content 正文
     * @param title   标题
     * @param ip      更新请求地 ip 地址
     * @return 更新结果
     */
    @Override
    public UnifyResponse<String> updateArticleById(long id, long userId, String content, String title, String ip) {
        long articleId = articleMapper.selectUserIdByIdInt(id);
        if (articleId == userId) {
            // 构建更新的文章实体
            Article article = new Article();
            article.setId((int) id);
            article.setContent(content);
            // 持久层数据更新
            articleMapper.updateByPrimaryKeySelective(article);
            return UnifyResponse.ok("更新成功！");
        }
        return UnifyResponse.fail("所有权错误，更新失败！");
    }

    /**
     * 上传文章
     *
     * @param id      用户 id
     * @param content 正文
     * @param title   标题
     * @return 上传结果
     */
    @Override
    public UnifyResponse<String> upload(long id, String content, String title) {
        //构建新的文章
        Article article = new Article();
        article.setUserId((int) id);
        article.setCreateTime(new Timestamp(System.currentTimeMillis()));
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        article.setDeleteStatus(false);
        article.setContent(content);
        article.setTitle(title);
        article.setPageView(0);
        article.setLikeNum(0);
        article.setStatus(0);
        // 持久层数据插入
        int result = articleMapper.insert(article);
        if (result > 0) {
            return UnifyResponse.ok("上传成功！");
        }
        return UnifyResponse.fail("上传失败！");
    }
}
