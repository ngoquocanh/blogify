package com.keybds.blogify.utils;

import com.keybds.blogify.dto.TagDTO;
import com.keybds.blogify.model.Tag;

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
