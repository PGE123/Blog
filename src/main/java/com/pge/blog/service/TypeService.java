package com.pge.blog.service;

import com.pge.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    void saveType(Type type);
    Type getType(Long id);
    Page<Type> listTypeByPage(Pageable pageable);
    List<Type> listByBlogSize(Integer size);
    void updateType(Long id,Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);
    List<Type> listType();

}
