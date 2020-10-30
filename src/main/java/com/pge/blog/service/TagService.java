package com.pge.blog.service;

import com.pge.blog.po.Tag;
import com.pge.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTagByPage(Pageable pageable);
    void updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);
    List<Tag> listTags();
    List<Tag> listTags(String tagIds);
    List<Tag> listTagByBlogSize(Integer size);

}
