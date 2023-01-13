package top.ninng.mapper;

import top.ninng.entity.ArticleTag;

/**
 * @author OhmLaw
 * @description 针对表【article_tag(文章标签信息表)】的数据库操作Mapper
 * @createDate 2023-01-13 19:21:58
 * @Entity top.ninng.entity.ArticleTag
 */
public interface ArticleTagMapper {

    /**
     * 根据 id 删除文章中额标签
     *
     * @param id 文章标签 id
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条文章标签关系
     *
     * @param articleTag 文章标签关系
     * @return 插入结果
     */
    int insert(ArticleTag articleTag);

    /**
     * 选择性插入一条文章标签关系
     *
     * @param record 文章标签关系
     * @return 插入结果
     */
    int insertSelective(ArticleTag record);

    /**
     * 根据文章标签关系 id 查询
     *
     * @param id 文章标签关系 id
     * @return 查询结果
     */
    ArticleTag selectByPrimaryKey(Long id);

    /**
     * 根据 id 更新文章标签关系
     *
     * @param record 文章标签关系 id
     * @return 更新结果
     */
    int updateByPrimaryKey(ArticleTag record);

    /**
     * 根据 id 选择性更新文章标签关系
     *
     * @param record 文章标签关系 id
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(ArticleTag record);

}
