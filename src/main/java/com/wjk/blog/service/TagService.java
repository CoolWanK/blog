package com.wjk.blog.service;

import com.wjk.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag getById(long id);
    Tag updateType(Long id,Tag tag);
    Tag saveTag(Tag tag);
    void delete(Long id);
    Page<Tag> listTag(Pageable pageable);
    Tag getByName(String name);
    List listTag();
    List<Tag> listTags(String ids);
    List<Tag>listTag(Integer size);
}
