package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.config.IdConfig;
import top.ninng.entity.Comment;
import top.ninng.entity.CommentResultItem;
import top.ninng.entity.UnifyResponse;
import top.ninng.entity.User;
import top.ninng.mapper.CommentMapper;
import top.ninng.mapper.UserMapper;
import top.ninng.service.ICommentService;
import top.ninng.utils.EmptyCheck;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;
import top.ninng.utils.Validator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 *
 * @Author OhmLaw
 * @Date 2023/1/11 18:31
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements ICommentService {

    CommentMapper commentMapper;
    UserMapper userMapper;
    IdObfuscator idObfuscator;
    GetConfig getConfig;
    int maxCommentLength;

    public CommentServiceImpl(CommentMapper commentMapper, UserMapper userMapper,
                              IdObfuscator idObfuscator, GetConfig getConfig) {
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.idObfuscator = idObfuscator;
        this.getConfig = getConfig;
        // 从数据库获取配置信息
        maxCommentLength = Integer.parseInt(getConfig.map().get("COMMENT_LENGTH"));
    }

    /**
     * 评论
     *
     * @param name      名称
     * @param email     邮件
     * @param content   中文
     * @param articleId 文章 id
     * @param userId    用户 id
     * @param parentId  父评论 id
     * @param ip        评论请求 ip 地址
     * @return 评论提交结果
     */
    @Override
    public UnifyResponse<String> comment(String name, String email, String content,
                                         Long articleId, Long userId, Long parentId, String ip) {
        if (userId != -1) {
            // 登录用户评论处理
            User user = userMapper.selectByPrimaryKey(userId);
            name = user.getUserName();
            email = user.getEmail();
        }
        if (Validator.isEmail(email)) {
            // 评论长度限制
            if (content.length() <= maxCommentLength) {
                Comment comment = new Comment();
                comment.setName(name);
                comment.setEmail(email);
                comment.setArticleId(Math.toIntExact(articleId));
                comment.setContent(content);
                comment.setUserId(Math.toIntExact(userId));
                comment.setParentId(Math.toIntExact(parentId));
                comment.setSite("localhost");
                comment.setIp(ip);
                comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
                comment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                // 持久层数据插入
                if ((commentMapper.insertSelective(comment) > 0)) {
                    return UnifyResponse.ok("评论成功！");
                }
                return UnifyResponse.ok("评论失败！");
            }
            return UnifyResponse.fail("评论过长！");
        }
        return UnifyResponse.fail("邮箱错误！");
    }

    /**
     * 获取指定评论的子评论
     *
     * @param id 评论 id
     * @return 子评论列表
     */
    @Override
    public UnifyResponse<ArrayList<Comment>> getChildCommentsById(Long id) {
        // 持久层数据查询
        ArrayList<Comment> resultItems = commentMapper.selectCommentByParentId(id);
        if (EmptyCheck.notEmpty(resultItems)) {
            return UnifyResponse.ok(resultItems);
        }
        return UnifyResponse.ok("暂无评论！", null);
    }

    /**
     * 获取指定文章 id 下的评论信息
     *
     * @param articleId 文章 id
     * @return 评论信息列表
     */
    @Override
    public UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(Long articleId) {
        // 持久层数据查询
        ArrayList<Comment> comments = commentMapper.selectCommentByArticleId(articleId);
        //  查询结果处理
        ArrayList<CommentResultItem> commentsResultList = comments.stream()
                // 保留根评论
                .filter(comment -> comment.getParentId() == -1)
                // id 混淆处理
                .peek(this::obfuscatorId)
                // 重新封装响应
                .map(comment -> new CommentResultItem(comment, null))
                // 转化输出列表
                .collect(Collectors.toCollection(ArrayList::new));
        // 处理根评论的一级子评论
        commentsResultList = commentsResultList.stream().peek(commentResultItem -> {
            ArrayList<Comment> commentsTemp =
                    comments.stream()
                            // 在当前文章下所有评论中查询子评论
                            .filter(comment -> comment.getParentId().equals(commentResultItem.getComment().getId()))
                            // id 混淆处理
                            .peek(this::obfuscatorId)
                            // 转换输出列表
                            .collect(Collectors.toCollection(ArrayList::new));
            if (EmptyCheck.notEmpty(commentsTemp)) {
                // 赋值子评论列表
                commentResultItem.setChildCommentList(commentsTemp);
            }
        }).collect(Collectors.toCollection(ArrayList::new));
        if (EmptyCheck.notEmpty(comments)) {
            return UnifyResponse.ok(commentsResultList);
        }
        return UnifyResponse.ok("暂无评论", null);
    }

    /**
     * 获取指定 id 的评论信息
     *
     * @param id 评论 id
     * @return 评论信息
     */
    @Override
    public UnifyResponse<CommentResultItem> getCommentById(Long id) {
        // 持久层数据查询当前评论信息
        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (EmptyCheck.notEmpty(comment)) {
            CommentResultItem commentResultItem = new CommentResultItem(comment, null);
            // 查询当前评论的子评论
            ArrayList<Comment> commentList = commentMapper.selectCommentByParentId(Long.valueOf(comment.getId()));
            if (EmptyCheck.notEmpty(commentList)) {
                if (commentList.size() > 0) {
                    // 赋值子评论
                    commentResultItem.setChildCommentList(commentList);
                }
            }
            return UnifyResponse.ok(commentResultItem);
        }
        return UnifyResponse.ok("暂无评论！", null);
    }

    /**
     * 对 id 进行混淆
     *
     * @param comment 评论
     */
    private void obfuscatorId(Comment comment) {
        comment.setObfuscatorId(idObfuscator.encode(comment.getId(), IdConfig.COMMENT_ID));
        comment.setObfuscatorArticleId(idObfuscator.encode(comment.getArticleId(), IdConfig.ARTICLE_ID));
        comment.setObfuscatorParentId(idObfuscator.encode(comment.getParentId(), IdConfig.COMMENT_ID));
        comment.setObfuscatorUserId(idObfuscator.encode(comment.getUserId(), IdConfig.USER_ID));
    }
}
