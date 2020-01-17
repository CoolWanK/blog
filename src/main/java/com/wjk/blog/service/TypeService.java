package com.wjk.blog.service;

import com.wjk.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);
    Type  getType(Long id);
    Page<Type> listType(Pageable pageable);
    Type updateType(Long id, Type type);
    List<Type> getTypeByName(String name);
    List<Type> listType();
    void deleteType(Long id);
    List<Type>listType(Integer size);
}
