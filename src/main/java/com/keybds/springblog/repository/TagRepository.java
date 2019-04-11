package com.keybds.springblog.repository;

import com.keybds.springblog.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

}
