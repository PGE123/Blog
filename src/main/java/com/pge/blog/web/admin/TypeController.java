package com.pge.blog.web.admin;

import com.pge.blog.po.Type;
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
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listTypeByPage(pageable));

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String addTypes(@Valid Type type, BindingResult bindingResult){
        Type t = typeService.getTypeByName(type.getName());
        if(t != null ){
            bindingResult.rejectValue("name","nameError","亲，分类名称重复了！！");
        }
        if(bindingResult.hasErrors())
            return "admin/types-input";
        typeService.saveType(type);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String addTypes(@Valid Type type, BindingResult bindingResult,@PathVariable Long id){
        Type t = typeService.getTypeByName(type.getName());
        if(t != null ){
            bindingResult.rejectValue("name","nameError","亲，分类名称重复了！！");
        }
        if(bindingResult.hasErrors())
            return "admin/types-input";
        typeService.saveType(type);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }



}
