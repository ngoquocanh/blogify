package com.keybds.springblog.repository;

import com.keybds.springblog.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

}
