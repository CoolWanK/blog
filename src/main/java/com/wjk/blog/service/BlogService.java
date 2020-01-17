package com.wjk.blog.service;

import com.wjk.blog.po.Blog;
import com.wjk.blog.vo.QueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, QueryVo blog);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void  deleterBlog(Long id);
    Page<Blog> listBlog(Pageable pageable);
    List<Blog>listBlog(Integer size);
    Page<Blog>listBlog(String query,Pageable pageable);
    Blog getAndConverse(long id);
    Page<Blog>listBlog(Long tagId,Pageable pageable);
    Map<String,List<Blog>>archivesMap();
    Long count();
}
