package com.pge.blog.service;

import com.pge.blog.NotFoundException;
import com.pge.blog.dao.BlogRepository;
import com.pge.blog.po.Blog;
import com.pge.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

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
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public void update(Blog blog) {
        Blog b = blogRepository.getOne(blog.getId());
        if(b == null)
            throw new NotFoundException("亲，你要跟新的blog不存在!!!");
        BeanUtils.copyProperties(blog,b);
        blogRepository.save(blog);
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
