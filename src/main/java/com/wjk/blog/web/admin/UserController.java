package com.wjk.blog.web.admin;

import com.wjk.blog.po.User;
import com.wjk.blog.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserSerivce userSerivce;
    @GetMapping
    public String login(){
        return "admin/login";
    }
    @PostMapping("login")
    public String check(@RequestParam(value = "username")String username, @RequestParam(value = "password") String password, HttpSession session, RedirectAttributes attributes , HttpServletResponse response) throws IOException {
        User user=userSerivce.checkLogin(username,password);
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {

            attributes.addFlashAttribute("message","密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/admin";
    }
    @GetMapping("blogs")
    public String blogs(){
        return "/admin/blogs";
    }
}
