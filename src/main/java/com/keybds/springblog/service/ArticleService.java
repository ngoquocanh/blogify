package com.keybds.springblog.service;

import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    /**
     * get public post at post page
     * @return
     */
    List<Article> getAllPublishedPosts();

    /**
     * get all public post for pagination
     * @param pageable
     * @return
     */
    Page<Article> getAllPublishedPosts(Pageable pageable);

    /**
     * get all post don't care status at admin post page
     * @return
     */
    List<Article> getAllPosts();

    /**
     * get all post for pagination
     * @param pageable
     * @return
     */
    Page<Article> getAllPosts(Pageable pageable);

    Page<Article> getAllPosts(Integer pageIndex, Integer pageSize, String sortName, String sortDir);

    /**
     * get post by name (or slug)
     * @param postName
     * @return
     * @throws MvcException
     */
    Article getPost(String postName) throws MvcException;

    /**
     * get post by id
     * @param postId
     * @return
     * @throws MvcException
     */
    Article getPost(Long postId) throws MvcException;

    Article updatePost(Article article) throws MvcException;

    Article createPost(Article article) throws MvcException;

    Boolean existsByName(String articleName) throws MvcException;

    /**
     * create slug by title
     * if slug was duplicated concat slug with current date
     * if slug and current date was duplicate then concat slug with random number
     * if slug and random number was duplicate throw an exception
     * @param articleTitle
     * @return
     * @throws MvcException
     */
    String createSlug(String articleTitle) throws MvcException;

    /**
     * delete single post
     * @param postId
     * @throws MvcException
     */
    void deletePost(Long postId) throws MvcException;

    /**
     * delete multi posts
     * @param postIds
     * @throws MvcException
     */
    void deletePosts(List<Long> postIds) throws MvcException;
}
