package com.quopri.blogify.utils;

import com.quopri.blogify.dto.PostDTO;
import com.quopri.blogify.entity.Article;
import com.quopri.blogify.entity.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PostUtil {

    /**
     * current UI just update or create with article status, title, excerpt, content
     * @param postDTO
     * @param isNew -> true for create new, false for update
     * @return
     */
    public static Article convertToEntity(PostDTO postDTO, boolean isNew) {
        Article article = new Article();
        if (!isNew) {
            article.setId(postDTO.getPostId());
        }
        article.setArticleStatus(Article.Status.findByKey(postDTO.getPostStatus()));
        article.setArticleExcerpt(postDTO.getPostExcerpt());
        article.setArticleTitle(postDTO.getPostTitle());
        article.setArticleImage(postDTO.getPostThumbnail());
        article.setArticleName(postDTO.getPostName());
        article.setArticleContent(postDTO.getPostContent());
        String[] postTagValues = Arrays.stream(postDTO.getPostTags().split(",")).map(String::trim).toArray(String[]::new);
        Set<Tag> tags = new HashSet<>();
        for (String postTag : postTagValues) {
            Tag tag = new Tag();
            tag.setValue(postTag);
            tags.add(tag);
        }
        article.setTags(tags);
        return article;
    }

    public static PostDTO convertToDTO(Article article) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(article.getId());
        postDTO.setPostTitle(article.getArticleTitle());
        postDTO.setPostThumbnail(article.getArticleImage());
        postDTO.setPostExcerpt(article.getArticleExcerpt());
        postDTO.setPostContent(article.getArticleContent());
        postDTO.setPostStatus(article.getArticleStatus().getKey());
        return postDTO;
    }
}
