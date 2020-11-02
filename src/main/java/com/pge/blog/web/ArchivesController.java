package com.pge.blog.web;

import com.pge.blog.po.Blog;
import com.pge.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.listGroupByYear());
        model.addAttribute("blogCount",blogService.blogCount());
        return "archives";
    }

}
