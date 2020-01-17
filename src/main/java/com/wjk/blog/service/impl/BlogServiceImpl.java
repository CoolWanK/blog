package com.wjk.blog.service.impl;

import com.wjk.blog.dao.BlogRepository;
import com.wjk.blog.handler.NotFoundException;
import com.wjk.blog.po.Blog;
import com.wjk.blog.po.Type;
import com.wjk.blog.service.BlogService;
import com.wjk.blog.util.MarkdownUtils;
import com.wjk.blog.util.MyBeanUtil;
import com.wjk.blog.vo.QueryVo;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
   @Autowired
    private BlogRepository repository;
    @Override
    public Blog getBlog(Long id) {
        return repository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, QueryVo blog) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId()!=null) {
                    predicates.add(criteriaBuilder.equal(root.<Long>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));//查询的维数组转化list为数组
                return null;

            }
        }, pageable);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()!=null){
            blog.setUpdateTime(new Date());
        }else {
            blog.setCreatTime(new Date());
            blog.setViews(0);
            blog.setUpdateTime(new Date());
        }
        return repository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=repository.getOne(id);
        if (b==null){
            throw new NotFoundException("不存在此类型");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtil.getNullPropertyName(blog));
        b.setUpdateTime(new Date());
        return repository.save(b);
    }
    @Transactional
    @Override
    public void deleterBlog(Long id) {
            repository.delete(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Blog> listBlog(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0,size,sort);
        return repository.findBlogTop(pageable);
    }
    @Transactional
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return repository.findByQuery(query,pageable);
    }

    @Override
    @Transactional
    public Blog getAndConverse(long id) {
        Blog blog=repository.getOne(id);
        if (blog == null) {
            throw new NotFoundException("不存在此对象");
        }
        repository.updateViews(id);
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));


        return b;
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join=root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archivesMap() {
        List<String>years=repository.findYears();
        Map<String,List<Blog>>map=new HashMap<>();
        for (String year:years){
            map.put(year,repository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
