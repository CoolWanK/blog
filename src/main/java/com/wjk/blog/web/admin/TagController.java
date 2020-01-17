package com.wjk.blog.web.admin;

import com.wjk.blog.po.Tag;
import com.wjk.blog.po.Type;
import com.wjk.blog.service.TagService;
import groovy.util.IFileNameFinder;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService service;
    @RequestMapping("/tag")
    public String tags(@PageableDefault(size = 5,sort ={"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", service.listTag(pageable));
        return "admin/tags";
    }
    //跳转到发布页面
    @RequestMapping("/tag/input")
    public String tagsInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @PostMapping("/tag/input-tag")
    public String tagInputs(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag t=service.getByName(tag.getName());
        if (t!=null){
            result.rejectValue("name","nameError","不能提交重复的标签");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1=service.saveTag(tag);
        if (tag1!=null){
            attributes.addFlashAttribute("message","添加成功");
        }else{
            attributes.addFlashAttribute("message","添加失败");
        }

        return "redirect:/admin/tag";
    }
    @GetMapping("/tag/{id}/get")
    public String getType(@PathVariable Long id, Model model){
        model.addAttribute("tag",service.getById(id));
        return "admin/tags-input";
    }
    @PostMapping("/tag/{id}/input-tag")
    public String updateType(@Valid Tag tag,BindingResult result,RedirectAttributes attributes,@PathVariable Long id){
        if (tag.getName().equals(service.getById(id).getName())){
            result.rejectValue("name","nameError","修改的标签不能与原来的一样");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1=service.updateType(id,tag);
        if (tag1!=null){
            attributes.addFlashAttribute("message","修改成功");
        }else {
            attributes.addFlashAttribute("message","修改失败");
        }
        return "redirect:/admin/tag";
    }
    @RequestMapping("/tag/{id}/delete")
    public String deleteType(@PathVariable Long id){
        service.delete(id);
        return "redirect:/admin/tag";
    }
}

