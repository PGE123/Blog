package com.pge.blog.service;

import com.pge.blog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    void save(Comment comment);

}
