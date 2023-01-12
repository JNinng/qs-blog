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
        maxCommentLength = Integer.parseInt(getConfig.map().get("COMMENT_LENGTH"));
    }

    @Override
    public UnifyResponse<String> comment(String name, String email, String content,
                                         Long articleId, Long userId, Long parentId, String ip) {
        if (userId != -1) {
            User user = userMapper.selectByPrimaryKey(userId);
            name = user.getUserName();
            email = user.getEmail();
        }
        if (Validator.isEmail(email)) {
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
                if ((commentMapper.insertSelective(comment) > 0)) {
                    return UnifyResponse.ok("评论成功！");
                }
                return UnifyResponse.ok("评论失败！");
            }
            return UnifyResponse.fail("评论过长！");
        }
        return UnifyResponse.fail("邮箱错误！");
    }

    @Override
    public UnifyResponse<ArrayList<Comment>> getChildCommentsById(Long id) {
        ArrayList<Comment> resultItems = commentMapper.selectCommentByParentId(id);
        if (EmptyCheck.notEmpty(resultItems)) {
            return UnifyResponse.ok(resultItems);
        }
        return UnifyResponse.ok("暂无评论！", null);
    }

    @Override
    public UnifyResponse<ArrayList<CommentResultItem>> getCommentByArticleId(Long articleId) {
        ArrayList<Comment> comments = commentMapper.selectCommentByArticleId(articleId);
        ArrayList<CommentResultItem> commentsResultList = comments.stream()
                .filter(comment -> comment.getParentId() == -1)
                .peek(this::obfuscatorId)
                .map(comment -> new CommentResultItem(comment, null))
                .collect(Collectors.toCollection(ArrayList::new));
        commentsResultList = commentsResultList.stream().peek(commentResultItem -> {
            ArrayList<Comment> commentsTemp =
                    comments.stream()
                            .filter(comment -> comment.getParentId().equals(commentResultItem.getComment().getId()))
                            .peek(this::obfuscatorId)
                            .collect(Collectors.toCollection(ArrayList::new));
            if (EmptyCheck.notEmpty(commentsTemp)) {
                commentResultItem.setChildCommentList(commentsTemp);
            }
        }).collect(Collectors.toCollection(ArrayList::new));
        if (EmptyCheck.notEmpty(comments)) {
            return UnifyResponse.ok(commentsResultList);
        }
        return UnifyResponse.ok("暂无评论", null);
    }

    @Override
    public UnifyResponse<CommentResultItem> getCommentById(Long id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        if (EmptyCheck.notEmpty(comment)) {
            CommentResultItem commentResultItem = new CommentResultItem(comment, null);
            ArrayList<Comment> commentList = commentMapper.selectCommentByParentId(Long.valueOf(comment.getId()));
            if (EmptyCheck.notEmpty(commentList)) {
                if (commentList.size() > 0) {
                    commentResultItem.setChildCommentList(commentList);
                }
            }
            return UnifyResponse.ok(commentResultItem);
        }
        return UnifyResponse.ok("暂无评论！", null);
    }

    private void obfuscatorId(Comment comment) {
        comment.setObfuscatorId(idObfuscator.encode(comment.getId(), IdConfig.COMMENT_ID));
        comment.setObfuscatorArticleId(idObfuscator.encode(comment.getArticleId(), IdConfig.ARTICLE_ID));
        comment.setObfuscatorParentId(idObfuscator.encode(comment.getParentId(), IdConfig.COMMENT_ID));
        comment.setObfuscatorUserId(idObfuscator.encode(comment.getUserId(), IdConfig.USER_ID));
    }
}
