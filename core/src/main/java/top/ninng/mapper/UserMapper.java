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

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条完整用户信息
     *
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 选择性插入
     *
     * @param user
     * @return
     */
    int insertSelective(User user);

    /**
     * 根据名称查找用户信息
     *
     * @param name
     * @return
     */
    User selectByName(String name);

    /**
     * 根据id查找用户信息
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    int updateByPrimaryKey(User user);

    /**
     * 选择性更新用户信息
     *
     * @param user
     * @return
     */
    int updateByPrimaryKeySelective(User user);
}
