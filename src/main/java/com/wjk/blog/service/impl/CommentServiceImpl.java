package com.wjk.blog.service.impl;

import com.wjk.blog.dao.CommentRespositroy;
import com.wjk.blog.po.Comment;
import com.wjk.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRespositroy commentRespositroy;
    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentRespositroy.findOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRespositroy.save(comment);
    }
    @Transactional
    @Override
    public List<Comment> listComment(Long blogId) {
        Sort sort=new Sort("createTime");
        return eachComment(commentRespositroy.findByBlogIdAndParentCommentNull(blogId,sort));
    }
    private List<Comment>eachComment(List<Comment> comments){
        List<Comment>commentsView=new ArrayList<>();
        for (Comment comment:comments){
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }
    private void combineChildren(List<Comment> comments){
        for (Comment comment:comments){
            List<Comment>replys=comment.getReplyComment();
            for (Comment reply:replys){
                recursively(reply);
            }
            comment.setReplyComment(tempReplys);
            tempReplys=new ArrayList<>();
        }
    }
    private List<Comment>tempReplys=new ArrayList<>();
    private void recursively(Comment comment){
        tempReplys.add(comment);
        if (comment.getReplyComment().size()>0){
            for (Comment reply:comment.getReplyComment()){
                tempReplys.add(reply);
                if (reply.getReplyComment().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
