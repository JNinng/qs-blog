package top.ninng.mapper;

import top.ninng.entity.Tag;

/**
 * @author OhmLaw
 * @description 针对表【tag(标签)】的数据库操作Mapper
 * @createDate 2023-01-13 19:18:52
 * @Entity top.ninng.entity.Tag
 */
public interface TagMapper {

    /**
     * 根据标签 id 删除
     *
     * @param id 标签 id
     * @return 删除结果
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条标签信息
     *
     * @param tag 标签
     * @return 插入结果
     */
    int insert(Tag tag);

    /**
     * 选择性插入一条标签信息
     *
     * @param record 标签
     * @return 插入结果
     */
    int insertSelective(Tag record);

    /**
     * 根据 id 查询标签信息
     *
     * @param id 标签 id
     * @return 查询结果
     */
    Tag selectByPrimaryKey(Long id);

    /**
     * 根据 id 更新标签信息
     *
     * @param record 标签 id
     * @return 更新结果
     */
    int updateByPrimaryKey(Tag record);

    /**
     * 根据 id 选择性更新标签信息
     *
     * @param record 标签
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(Tag record);

}
