package com.wjk.blog.service.impl;

import com.wjk.blog.dao.TypeRepository;
import com.wjk.blog.handler.NotFoundException;
import com.wjk.blog.po.Type;
import com.wjk.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.SortedMap;

@Service
public class TypeServiceImpl implements TypeService {
   @Autowired
   private TypeRepository typeRepository;
    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.findOne(id);
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public List<Type> getTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }
    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
    @Transactional
    @Override
    public List<Type> listType(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return typeRepository.listTypeTop(pageable);
    }
}
