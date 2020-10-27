package com.pge.blog.web.admin;

import com.pge.blog.po.Tag;
import com.pge.blog.po.Type;
import com.pge.blog.service.TagService;
import com.pge.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",tagService.listTagByPage(pageable));

        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String addTypes(@Valid Tag tag, BindingResult bindingResult){
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null ){
            bindingResult.rejectValue("name","nameError","亲，标签名称重复了！！");
        }
        if(bindingResult.hasErrors())
            return "admin/tags-input";
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String addTypes(@Valid Tag tag, BindingResult bindingResult,@PathVariable Long id){
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null ){
            bindingResult.rejectValue("name","nameError","亲，标签名称重复了！！");
        }
        if(bindingResult.hasErrors())
            return "admin/tags-input";
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id){
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }



}
