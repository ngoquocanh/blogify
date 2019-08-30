package com.quopri.blogify.service;

import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    List<String> getAllTagValues();
    Page<Tag> getAllTags(Pageable pageable);
    void deleteTags(List<Long> tagIds) throws MvcException;
    Tag getTag(Long tagId) throws MvcException;
    Tag getTag(String value) throws MvcException;
    Tag updateTag(Tag tag) throws MvcException;
    Tag createTag(Tag tag) throws MvcException;
}
