package com.wjk.blog.dao;

import com.wjk.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
   Tag getByName(String name);
   @Query("SELECT t FROM Tag t")
   List<Tag>listTag(Pageable pageable);
}
