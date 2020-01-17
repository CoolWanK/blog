package com.wjk.blog.service;

import com.wjk.blog.po.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);
    List<Comment> listComment(Long blogId );
}
