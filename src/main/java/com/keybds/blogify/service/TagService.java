package com.keybds.blogify.service;

import com.keybds.blogify.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Page<Tag> getAllTags(Pageable pageable);
}
