package top.ninng.mapper;

import top.ninng.entity.Tag;

/**
 * @author OhmLaw
 * @description 针对表【tag(标签)】的数据库操作Mapper
 * @createDate 2023-01-13 19:18:52
 * @Entity top.ninng.entity.Tag
 */
public interface TagMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Tag record);

    int updateByPrimaryKeySelective(Tag record);

}
