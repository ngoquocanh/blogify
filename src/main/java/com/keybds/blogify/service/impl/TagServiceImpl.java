package com.keybds.blogify.service.impl;

import com.keybds.blogify.model.Tag;
import com.keybds.blogify.repository.TagRepository;
import com.keybds.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl extends AbstractService implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        if (isPaged(pageable)) {
            return tagRepository.findAll(pageable);
        } else {
            return Page.empty();
        }
    }
}
