package com.keybds.blogify.service.impl;

import com.keybds.blogify.enums.StatusMessageCode;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Tag;
import com.keybds.blogify.repository.TagRepository;
import com.keybds.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void deleteTags(List<Long> tagIds) throws MvcException {
        for (Long tagId : tagIds) {
            if (tagRepository.existsById(tagId)) {
                tagRepository.deleteById(tagId);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Tag getTag(Long tagId) throws MvcException {
        Tag tag;
        if (tagRepository.existsById(tagId)) {
            Optional<Tag> tagExisted = tagRepository.findById(tagId);
            if (tagExisted.isPresent()) {
                tag = tagExisted.get();
            } else {
                throw new MvcException(StatusMessageCode.TAG_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.TAG_NOT_FOUND);
        }
        return tag;
    }

    @Override
    public Tag updateTag(Tag tag) throws MvcException {
        Tag tagUpdated;
        if (tagRepository.existsById(tag.getId())) {
            Optional<Tag> optionalTag = tagRepository.findById(tag.getId());
            if (optionalTag.isPresent()) {
                Tag tagToUpdate = new Tag();
                tagToUpdate.setId(optionalTag.get().getId());
                tagToUpdate.setValue(tag.getValue());
                tagUpdated = tagRepository.save(tagToUpdate);
            } else {
                throw new MvcException(StatusMessageCode.TAG_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.TAG_NOT_FOUND);
        }
        return tagUpdated;
    }
}
