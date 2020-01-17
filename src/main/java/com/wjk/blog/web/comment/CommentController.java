package com.wjk.blog.web.comment;

import com.wjk.blog.handler.NotFoundException;
import com.wjk.blog.po.Blog;
import com.wjk.blog.po.Comment;
import com.wjk.blog.po.User;
import com.wjk.blog.service.BlogService;
import com.wjk.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    @PostMapping("/comment")
    public String postComment(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        Blog blog=blogService.getBlog(blogId);
        comment.setBlog(blog);
       User user= (User) session.getAttribute("user");
       if (user!=null){
           comment.setAdminComment(true);
           comment.setAvatar(user.getAvatar());
           comment.setNickname(user.getNickname());
       }else {
           comment.setAdminComment(false);
           comment.setAvatar(avatar);
       }

        commentService.saveComment(comment);
        return "redirect:/comment/"+blogId;

    }
    @GetMapping("/comment/{blogId}")
    public String listByBlogId(@PathVariable Long blogId, Model model){
        List<Comment>comments=commentService.listComment(blogId);
        model.addAttribute("comment",comments);
        return "blog::commentList";
    }
    @PostMapping("/comments")
    public String commentChange(Comment comment){
       Long blogId= comment.getBlog().getId();
       return "redirect:/comment/"+blogId;
    }

}
