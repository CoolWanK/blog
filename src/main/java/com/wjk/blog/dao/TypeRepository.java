package com.wjk.blog.dao;

import com.wjk.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    List<Type>findTypeByName(String name);
    @Query("select t from Type t")
    List<Type>listTypeTop(Pageable pageable);
}
