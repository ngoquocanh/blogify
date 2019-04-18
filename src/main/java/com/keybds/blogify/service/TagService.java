package com.keybds.blogify.service;

import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Page<Tag> getAllTags(Pageable pageable);
    void deleteTags(List<Long> tagIds) throws MvcException;
    Tag getTag(Long tagId) throws MvcException;
    Tag updateTag(Tag tag) throws MvcException;
}
