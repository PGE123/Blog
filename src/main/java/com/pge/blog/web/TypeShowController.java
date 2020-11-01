package com.pge.blog.web;

import com.pge.blog.po.Type;
import com.pge.blog.service.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        Model model,
                        @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        List<Type> types = typeService.listType();
        if(id == -1){
           id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listByPage(pageable,blogQuery));
        model.addAttribute("activeId",id);
        return "types";
    }

}
