package com.pge.blog.service;

import com.pge.blog.po.Blog;
import com.pge.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
     void save(Blog blog);
     Page<Blog> listByPage(Pageable pageable, BlogQuery blog);
     Blog getBlog(Long id);
     void update(Blog blog);
     void delete(Long id);
}
