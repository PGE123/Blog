package com.pge.blog.web.admin;

import com.pge.blog.po.Blog;
import com.pge.blog.po.User;
import com.pge.blog.service.BlogService;
import com.pge.blog.service.TagService;
import com.pge.blog.service.TypeService;
import com.pge.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    final static private String LIST = "admin/admin";
    final static private String redirectLIST= "redirect:/admin/admin";
    final static private String INPUT = "admin/admin-release";

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog,
                        Model model){
        model.addAttribute("page",blogService.listByPage(pageable,blog));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/searchBlog")
    public String SearchBlog(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog,
                        Model model){
        model.addAttribute("page",blogService.listByPage(pageable,blog));
        return "admin/admin :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTags());
        return INPUT;
    }

    @PostMapping("/blogs/add")
    public String add(Blog blog, HttpSession httpSession){
        blog.setUser((User) httpSession.getAttribute("user"));
        return redirectLIST;
    }
}
