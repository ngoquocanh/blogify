package com.keybds.springblog.repository;

import com.keybds.springblog.model.ArticleStatus;
import org.springframework.data.repository.CrudRepository;

public interface ArticleStatusRepository extends CrudRepository<ArticleStatus, Integer> {

}
