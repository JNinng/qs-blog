package top.ninng.mapper;

import org.springframework.stereotype.Repository;
import top.ninng.entity.User;

/**
 * @author OhmLaw
 * @description 针对表【user(用户信息表)】的数据库操作Mapper
 * @createDate 2023-01-01 14:20:53
 * @Entity top.ninng.entity.User
 */
@Repository("userMapper")
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByName(String name);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);
}
