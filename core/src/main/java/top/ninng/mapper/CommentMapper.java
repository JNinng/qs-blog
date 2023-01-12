package top.ninng.mapper;

import org.springframework.stereotype.Repository;
import top.ninng.entity.Comment;

import java.util.ArrayList;

/**
 * @author OhmLaw
 * @description 针对表【comment(评论信息表)】的数据库操作Mapper
 * @createDate 2023-01-11 16:35:13
 * @Entity top.ninng.entity.Comment
 */
@Repository("commentMapper")
public interface CommentMapper {

    /**
     * 根据 id 删除评论
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条完整评论
     *
     * @param record
     * @return
     */
    int insert(Comment record);

    /**
     * 选择性插入一条评论
     *
     * @param record
     * @return
     */
    int insertSelective(Comment record);

    /**
     * 根据 id 查询评论
     *
     * @param id
     * @return
     */
    Comment selectByPrimaryKey(Long id);

    /**
     * 根据文章 id 查询评论
     *
     * @param articleId
     * @return
     */
    ArrayList<Comment> selectCommentByArticleId(Long articleId);

    /**
     * 根据父评论 id 查询评论
     *
     * @param parentId
     * @return
     */
    ArrayList<Comment> selectCommentByParentId(Long parentId);

    /**
     * 根据 id 更新一条评论
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Comment record);

    /**
     * 根据 id 选择性更新一条评论
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Comment record);

}
