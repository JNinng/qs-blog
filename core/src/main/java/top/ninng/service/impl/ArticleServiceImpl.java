package top.ninng.service.impl;

import org.springframework.stereotype.Service;
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

    @Override
    public UnifyResponse<Article> getArticleById(long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setObfuscatorId(idObfuscator.encode(article.getId(), 1));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), 1));
        return UnifyResponse.ok(article);
    }

    @Override
    public UnifyResponse<ArticleIdListPageResult> getArticleIdListByPage(int page, int pageSize) {
        page = (page <= 0) ? 1 : page;
        ArrayList<String> articleIdList =
                articleMapper.selectArticleIdListByPage((page - 1) * pageSize, pageSize)
                        .stream()
                        .map(aLong -> idObfuscator.encode(Math.toIntExact(aLong), 1))
                        .collect(Collectors.toCollection(ArrayList::new));
        return UnifyResponse.ok(new ArticleIdListPageResult(articleIdList, page, pageSize));
    }

    @Override
    public UnifyResponse<Article> getArticleInfoById(long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setObfuscatorId(idObfuscator.encode(article.getId(), 1));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), 1));
        article.setContent("");
        return UnifyResponse.ok(article);
    }

    @Override
    public UnifyResponse<Article> getArticlePreviewById(long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setObfuscatorId(idObfuscator.encode(article.getId(), 1));
        article.setObfuscatorUserId(idObfuscator.encode(article.getUserId(), 1));
        String content = article.getContent();
        if (content.indexOf("<!-- more -->") > 0) {
            article.setContent(content.split("<!-- more -->\n")[0]);
        } else {
            article.setContent("");
        }
        return UnifyResponse.ok(article);
    }

    @Override
    public UnifyResponse<String> updateArticleById(long id, long userId, String content, String title, String ip) {
        long articleId = articleMapper.selectUserIdByIdInt(id);
        if (articleId == userId) {
            Article article = new Article();
            article.setId((int) id);
            article.setContent(content);
            articleMapper.updateByPrimaryKeySelective(article);
            return UnifyResponse.ok("更新成功！");
        }
        return UnifyResponse.fail("所有权错误，更新失败！");
    }

    @Override
    public UnifyResponse<String> upload(long id, String content, String title) {
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
        int result = articleMapper.insert(article);
        if (result > 0) {
            return UnifyResponse.ok("上传成功！");
        }
        return UnifyResponse.fail("上传失败！");
    }
}
