package com.wjk.blog.web;

import com.wjk.blog.po.Blog;
import com.wjk.blog.po.Type;
import com.wjk.blog.service.BlogService;
import com.wjk.blog.service.TypeService;
import com.wjk.blog.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String showType(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types=typeService.listType();
        if (id==-1){
            id=types.get(0).getId();
        }
        QueryVo queryVo=new QueryVo();
        queryVo.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,queryVo));
        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "types";
    }
}
