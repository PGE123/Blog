package com.pge.blog.service;

import com.pge.blog.NotFoundException;
import com.pge.blog.dao.TypeRepository;
import com.pge.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public void saveType(Type type) {
        typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listTypeByPage(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listByBlogSize(Integer size) {
        Sort sort = Sort.by(Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.listTypeByBlogSize(pageable) ;
    }

    @Transactional
    @Override
    public void updateType(Long id,Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
}
