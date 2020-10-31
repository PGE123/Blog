package com.pge.blog.web;

import com.pge.blog.service.BlogService;
import com.pge.blog.service.TagService;
import com.pge.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;


    @RequestMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.list(pageable));
        model.addAttribute("types",typeService.listByBlogSize(5));
        model.addAttribute("tags",tagService.listTagByBlogSize(10));
        model.addAttribute("recommendBlogs",blogService.listBlogByRecommend(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam("searchContent") String searchContent,
                         Model model){
        model.addAttribute("page",blogService.listSearchBlog("%"+searchContent+"%",pageable));
        model.addAttribute("searchContent",searchContent);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
