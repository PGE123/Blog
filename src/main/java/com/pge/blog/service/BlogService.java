package com.pge.blog.service;

import com.pge.blog.po.Blog;
import com.pge.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
     void save(Blog blog);
     Page<Blog> listByPage(Pageable pageable, BlogQuery blog);
     Page<Blog> list(Pageable pageable);
     Page<Blog> listSearchBlog(String searchContent,Pageable pageable);
     Page<Blog> list(Long tagId,Pageable pageable);
     Blog getBlog(Long id);
     Blog getAndConvert(Long id);
     void update(Long id,Blog blog);
     void delete(Long id);
     List<Blog> listBlogByRecommend(Integer size);
     Map<String,List<Blog>> listGroupByYear();
     Long blogCount();
}
