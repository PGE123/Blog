package com.pge.blog.service;

import com.pge.blog.NotFoundException;
import com.pge.blog.dao.TagRepository;
import com.pge.blog.dao.TypeRepository;
import com.pge.blog.po.Tag;
import com.pge.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTagByPage(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void updateTag(Long id,Tag tag) {
        Tag t = tagRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTags(String tagIds) {
        return tagRepository.findAllById(convertToList(tagIds));
    }

    private List<Long> convertToList(String tagIds){
        List<Long> list = new ArrayList<>();
        if(tagIds.length()!=0){
            String[] tagId = tagIds.split(",");
            for(int i=0;i<tagId.length;i++){
                list.add(Long.valueOf(tagId[i]));
            }
        }
        return list;
    }
}
