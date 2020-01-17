package com.wjk.blog.web.admin;

import com.wjk.blog.po.Type;
import com.wjk.blog.service.TypeService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @RequestMapping("/type")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/type/types-input")
    public String typeinput(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @PostMapping("/type/input")
    public String input(@Valid Type type,  BindingResult result,RedirectAttributes redirectAttributes){
        List<Type> typeList=typeService.getTypeByName(type.getName());
        if (typeList.size()>0&&typeList!=null){
            result.rejectValue("name","nameError","不能提交重复的分类");//自定义的错误信息
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.saveType(type);
        if (t==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/type";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/types-input";
    }
    @PostMapping("/type/{id}/input")
    public String editPost(@Valid Type type,BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes){
        List<Type> typeList=typeService.getTypeByName(type.getName());
        if (typeList.size()>0&&typeList!=null){
            result.rejectValue("name","nameError","不能提交重复的分类");//自定义的错误信息
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.updateType(id,type);
        if (t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/type";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id){
        typeService.deleteType(id);
        return "redirect:/admin/type";
    }
}
