package com.pge.blog.dao;

import com.pge.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    @Query("select t from Type t")
    List<Type> listTypeByBlogSize(Pageable pageable);
}
