package com.pge.blog.web;

import com.pge.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        System.out.println("------------index-----------");
        return "admin/admin";
    }
}
