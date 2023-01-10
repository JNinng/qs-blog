package top.ninng.mapper;

import org.springframework.stereotype.Repository;
import top.ninng.entity.Config;

import java.util.ArrayList;

/**
 * @author OhmLaw
 * @description 针对表【config(配置信息)】的数据库操作Mapper
 * @createDate 2023-01-10 19:56:51
 * @Entity top.ninng.entity.Config
 */
@Repository("configMapper")
public interface ConfigMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Config record);

    int insertSelective(Config record);

    ArrayList<Config> selectAll();

    Config selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Config record);

    int updateByPrimaryKeySelective(Config record);

}
