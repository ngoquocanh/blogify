package com.keybds.blogify.repository;

import com.keybds.blogify.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

}
