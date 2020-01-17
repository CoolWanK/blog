package com.wjk.blog.web;

import com.wjk.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowContreller {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("map",blogService.archivesMap());
        model.addAttribute("count",blogService.count());
        return "archives";
    }
}
