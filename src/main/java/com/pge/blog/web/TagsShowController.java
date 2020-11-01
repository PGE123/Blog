package com.pge.blog.web;

import com.pge.blog.po.Tag;
import com.pge.blog.service.BlogService;
import com.pge.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagsShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id,
                       Model model,
                       @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        List<Tag> tags = tagService.listTags();

        if(id == -1){
            id = tags.get(0).getId();
        }

        model.addAttribute("tags",tags);
        model.addAttribute("activeId",id);
        model.addAttribute("page",blogService.list(id,pageable));
        return "tags";
    }

}
