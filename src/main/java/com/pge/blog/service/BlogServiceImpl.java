package com.pge.blog.service;

import com.pge.blog.NotFoundException;
import com.pge.blog.dao.BlogRepository;
import com.pge.blog.po.Blog;
import com.pge.blog.util.MarkdownUtils;
import com.pge.blog.util.MyBeanUtils;
import com.pge.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Transactional
    @Override
    public void save(Blog blog) {
        if(blog.getId() != null){
            blog.setUpdateTime(new Date());
        }else{
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Page<Blog> listByPage(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null)
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                if(blog.getTypeId() != null)
                    predicates.add(cb.equal(root.<Long>get("type").get("id"),blog.getTypeId()));
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> list(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listSearchBlog(String searchContent, Pageable pageable) {
        return blogRepository.listSearchBlog(searchContent,pageable);
    }

    @Override
    public Page<Blog> list(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join join =  root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public void update(Long id,Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b == null)
            throw new NotFoundException("亲，你要跟新的blog不存在!!!");
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        blogRepository.save(b);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listBlogByRecommend(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.listBlogByRecommend(pageable);
    }

    @Override
    public Map<String, List<Blog>> listGroupByYear() {
        Map<String,List<Blog>> map = new HashMap<>();
        List<String> years = blogRepository.listGroupYear();
        for(String year : years){
            map.put(year,blogRepository.listByYear(year));
        }
        return map;
    }

    @Override
    public Long blogCount() {
        return blogRepository.count();
    }
}
