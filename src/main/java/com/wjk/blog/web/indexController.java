package com.wjk.blog.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wjk.blog.handler.NotFoundException;
import com.wjk.blog.po.Blog;
import com.wjk.blog.service.BlogService;
import com.wjk.blog.service.TagService;
import com.wjk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class indexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @GetMapping("/")
    public String index(@PageableDefault(sort = {"updateTime"},size = 5,direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType(6));
        model.addAttribute("tags",tagService.listTag(10));
        model.addAttribute("recommendBlog",blogService.listBlog(5));
        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,direction = Sort.Direction.DESC,sort = {"updateTime"}) Pageable pageable,Model model,@RequestParam String query){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
            return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConverse(id));
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String get3Blog(Model model){
        model.addAttribute("newBlogs",blogService.listBlog(3));
        for (Blog b:blogService.listBlog(3)){
            System.out.println(b.getTitle());
        }
        return "_fragments::blogList";
    }

}
