package com.wjk.blog.dao;

import com.wjk.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRespositroy extends JpaRepository<Comment,Long> {
    List<Comment>findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
