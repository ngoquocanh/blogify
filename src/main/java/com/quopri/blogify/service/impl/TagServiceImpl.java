package com.quopri.blogify.service.impl;

import com.quopri.blogify.entity.ArticleTag;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.entity.Tag;
import com.quopri.blogify.repository.ArticleTagRepository;
import com.quopri.blogify.repository.TagRepository;
import com.quopri.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagServiceImpl extends AbstractService implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public List<String> getAllTagValues() {
        List<Tag> tags = getAllTags();
        List<String> tagValues = new ArrayList<>();
        for (Tag tag : tags) {
            tagValues.add(tag.getValue());
        }
        return tagValues;
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
                List<ArticleTag> articleTagList = articleTagRepository.findByTagId(tagId);
                if (articleTagList != null) {
                    articleTagRepository.deleteAll(articleTagList);
                }
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
    public Tag getTag(String value) throws MvcException {
        Tag tag = tagRepository.findByValueIgnoreCase(value);
        if (tag == null) {
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

    @Override
    public Tag createTag(Tag tag) throws MvcException {
        Tag tagToCreate = new Tag();
        tagToCreate.setValue(tag.getValue());
        return tagRepository.save(tagToCreate);
    }
}
