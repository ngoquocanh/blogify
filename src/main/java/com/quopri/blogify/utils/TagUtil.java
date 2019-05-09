package com.quopri.blogify.utils;

import com.quopri.blogify.dto.TagDTO;
import com.quopri.blogify.model.Tag;

public class TagUtil {

    public static TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setValue(tag.getValue());
        return tagDTO;
    }

    public static Tag convertToEntity(TagDTO tagDTO, boolean isNew) {
        Tag tag = new Tag();
        if (!isNew) {
            tag.setId(tagDTO.getId());
        }
        tag.setValue(tagDTO.getValue());
        return tag;
    }
}
