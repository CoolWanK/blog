package com.wjk.blog.service.impl;

import com.wjk.blog.dao.TagRepository;
import com.wjk.blog.handler.NotFoundException;
import com.wjk.blog.po.Tag;
import com.wjk.blog.service.TagService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository repository;
    @Override
    public Tag getById(long id) {
        return repository.getOne(id);
    }

    @Override
    public Tag updateType(Long id, Tag tag) {
        Tag t=repository.getOne(id);
        if (t==null){
            throw new NotFoundException("不存在此类型的标签");
        }
        BeanUtils.copyProperties(tag,t);
        return repository.save(t);
    }

    @Override
    public Tag saveTag(Tag tag) {
       return repository.save(tag);
    }

    @Override
    public void delete(Long id) {
            repository.delete(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Tag getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public List listTag() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List<Tag> listTags(String ids) {
        return repository.findAll(listIds(ids));

    }
    //将传递过来的tag id字符串转化成list的数组
    private List<Long> listIds(String ids){
      List<Long> list=new ArrayList<>();
      if (!"".equals(ids)&&ids!=null){
          String[] id=StringUtils.split(ids,",");
          for (int i=0;i<id.length;i++){
                list.add(new Long(id[i]));
          }
      }
      return list;
    }
    @Transactional
    @Override
    public List<Tag> listTag(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return repository.listTag(pageable);
    }
}
