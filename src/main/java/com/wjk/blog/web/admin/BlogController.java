package com.wjk.blog.web.admin;

import com.wjk.blog.po.Blog;
import com.wjk.blog.po.User;
import com.wjk.blog.service.BlogService;
import com.wjk.blog.service.TagService;
import com.wjk.blog.service.TypeService;
import com.wjk.blog.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/blog")
    public String blog(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, QueryVo blog, Model model){
           model.addAttribute("page",blogService.listBlog(pageable,blog));
           model.addAttribute("type",typeService.listType());
                return "admin/blogs";
    }
    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, QueryVo blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs::blogList";
    }
    @GetMapping("/blog/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return "admin/blogs-input";
    }
  @PostMapping("/blogs")
    public String input(Model model, HttpSession session, Blog blog, RedirectAttributes attributes){
       blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTags(blog.getTagIds()));
        Blog b;
        if (blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else {
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if (b!=null){
            attributes.addFlashAttribute("message","操作成功");
        }else {
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/blog";
    }
    @GetMapping("/blog/{id}/blog-edit")
    public String blogEdit(@PathVariable Long id,Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }
    @RequestMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id){
        blogService.deleterBlog(id);
        return "redirect:/admin/blog";
    }
}
