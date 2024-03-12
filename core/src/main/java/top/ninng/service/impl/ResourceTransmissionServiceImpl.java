package top.ninng.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.ninng.config.IdConfig;
import top.ninng.entity.Article;
import top.ninng.entity.Tag;
import top.ninng.entity.UnifyResponse;
import top.ninng.mapper.ArticleMapper;
import top.ninng.mapper.ArticleTagMapper;
import top.ninng.mapper.TagMapper;
import top.ninng.service.IResourceTransmission;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * @Author OhmLaw
 * @Date 2023/2/18 14:47
 * @Version 1.0
 */
@Slf4j
@Service
public class ResourceTransmissionServiceImpl implements IResourceTransmission {

    IdObfuscator idObfuscator;
    ArticleMapper articleMapper;
    ArticleTagMapper articleTagMapper;
    TagMapper tagMapper;
    GetConfig getConfig;
    RestTemplate restTemplate = new RestTemplate();

    public ResourceTransmissionServiceImpl(IdObfuscator idObfuscator, ArticleMapper articleMapper,
                                           ArticleTagMapper articleTagMapper, TagMapper tagMapper,
                                           GetConfig getConfig) {
        this.idObfuscator = idObfuscator;
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
        this.getConfig = getConfig;
    }

    private void createArticle(String title, String content, Date date, Article article) {
        article.setUserId(Integer.valueOf(getConfig.map().get("ROOT_USER")));
        article.setCreateTime(new Timestamp(date.getTime()));
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        article.setDeleteStatus(false);
        article.setContent(content);
        article.setTitle(title);
        article.setIp("127.0.0.1");
        article.setSite("本地");
        article.setPageView(0);
        article.setLikeNum(0);
        article.setStatus(0);
        article.setStick(0);
        article.setMode(article.getContent().contains("<!-- more -->") ? 1 : 0);
    }

    public String httpPush(String authorizationCode, String link, Article article, ArrayList<String> tag) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("authorizationCode", authorizationCode);
        params.add("mode", 0);
        params.add("title", article.getTitle());
        params.add("content", article.getContent());
        params.add("tag", tag);
        params.add("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getCreateTime()));

        log.debug(params.toString());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(link, HttpMethod.POST, request, String.class);
        return new String(Objects.requireNonNull(response.getBody()).getBytes("ISO8859-1"),
                StandardCharsets.UTF_8);
    }

    @Override
    public UnifyResponse<String> push(String authorizationCode, String link, String resourceId) {
        // 获取真实 id
        long[] realId = idObfuscator.decode(resourceId, IdConfig.ARTICLE_ID);
        if (realId.length > 0) {
            // 获取资源
            Article article = articleMapper.selectByPrimaryKey(realId[0]);
            if (EmptyCheck.notEmpty(article)) {
                // 封装标签
                ArrayList<Long> longArrayList = articleTagMapper.selectTagIdByArticleIdLongs(realId[0]);
                ArrayList<String> tagList = new ArrayList<String>();
                for (Long aLong : longArrayList) {
                    Tag tag = tagMapper.selectByPrimaryKey(aLong);
                    tagList.add(tag.getName());
                }
                try {
                    // 推送
                    return UnifyResponse.ok(httpPush(authorizationCode, link, article, tagList));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return UnifyResponse.fail("推送出错！", null);
                }
            }
        }
        return UnifyResponse.fail("id 错误！", null);
    }

    @Override
    public UnifyResponse<String> receive(String authorizationCode,
                                         int mode,
                                         String title,
                                         String content,
                                         ArrayList<String> tag,
                                         Date date) {
        System.out.println("接收到以下资源：");
        System.out.println(authorizationCode + " " + mode + " " + title + " " + content + " " + tag + " " + date);
        // 授权码判别
        if ("be205a15-014e-4f35-a42a-57ba6ef410d9".equals(authorizationCode)) {
            if (mode == 0) {
                //构建新的文章
                Article article = new Article();
                createArticle(title, content, date, article);
                // 持久层数据插入
                int result = articleMapper.insert(article);
                if (result > 0) {
                    return UnifyResponse.ok("接收成功！", null);
                }
                return UnifyResponse.fail("接收失败！", null);
            }
        }
        return UnifyResponse.fail("授权错误！", null);
    }
}
