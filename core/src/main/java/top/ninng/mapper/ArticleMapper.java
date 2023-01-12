package top.ninng.mapper;

import org.springframework.stereotype.Repository;
import top.ninng.entity.Article;

import java.util.ArrayList;

/**
 * @author OhmLaw
 * @description 针对表【article(文章信息表)】的数据库操作Mapper
 * @createDate 2023-01-06 16:51:55
 * @Entity top.ninng.entity.Article
 */
@Repository("articleManager")
public interface ArticleMapper {

    /**
     * 根据 id 删除文章
     *
     * @param id 文章 id
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入完整文章信息
     *
     * @param article 文章
     * @return 插入结果
     */
    int insert(Article article);

    /**
     * 选择性插入文章信息
     *
     * @param article 文章
     * @return 插入结果
     */
    int insertSelective(Article article);

    /**
     * 分页查询文章 id
     *
     * @param l 左边界
     * @param r 右边界
     * @return 分页查询 id 结果列表
     */
    ArrayList<Long> selectArticleIdListByPage(int l, int r);

    /**
     * 根据 id 查询文章信息
     *
     * @param id 文章 id
     * @return 文章信息
     */
    Article selectByPrimaryKey(Long id);

    /**
     * 根据 id 查找所属用户
     *
     * @param id 文章 id
     * @return 所属用户 id
     */
    long selectUserIdByIdInt(Long id);

    /**
     * 更新完整文章信息
     *
     * @param article 文章
     * @return 更新结果
     */
    int updateByPrimaryKey(Article article);

    /**
     * 选择性更新文章信息
     *
     * @param article 文章
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(Article article);
}
