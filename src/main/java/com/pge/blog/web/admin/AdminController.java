package com.pge.blog.web.admin;

import com.pge.blog.po.Blog;
import com.pge.blog.po.Tag;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String LIST = "admin/admin";
    private static final String redirectLIST= "redirect:/admin/blogs";
    private static final String INPUT = "admin/admin-release";

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
    public String blogsPage(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog,
                        Model model){
        model.addAttribute("page",blogService.listByPage(pageable,blog));
        return "admin/admin :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTagType(model);
        return INPUT;
    }

    private void setTagType(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTags());
    }

    @GetMapping("/blogs/{id}/edit")
    public String edit(@PathVariable Long  id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        setTagType(model);
        return INPUT;
    }

    @PostMapping("/blogs/release")
    public String add(Blog blog, HttpSession httpSession){
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTags(blog.getTagIds()));
        blog.setUser((User) httpSession.getAttribute("user"));
        blogService.save(blog);
        return redirectLIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id){
        blogService.delete(id);
        return redirectLIST;
    }
}
