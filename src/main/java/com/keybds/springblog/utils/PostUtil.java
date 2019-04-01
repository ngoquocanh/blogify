package com.keybds.springblog.utils;

import com.keybds.springblog.enums.ArticleEnum;
import com.keybds.springblog.model.ArticleStatus;
import com.keybds.springblog.dto.PostDTO;
import com.keybds.springblog.model.Article;

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
        if (postDTO.getPostStatus().equals(ArticleEnum.STATUS_PUBLIC.getKey())) {
            article.setArticleStatus(new ArticleStatus(ArticleEnum.STATUS_PUBLIC));
        } else if (postDTO.getPostStatus().equals(ArticleEnum.STATUS_DRAFT.getKey())) {
            article.setArticleStatus(new ArticleStatus(ArticleEnum.STATUS_DRAFT));
        } else {
            article.setArticleStatus(new ArticleStatus(ArticleEnum.STATUS_DRAFT));
        }
        article.setArticleExcerpt(postDTO.getPostExcerpt());
        article.setArticleTitle(postDTO.getPostTitle());
        article.setArticleName(postDTO.getPostName());
        article.setArticleContent(postDTO.getPostContent());
        return article;
    }

    public static PostDTO convertToDTO(Article article) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(article.getId());
        postDTO.setPostTitle(article.getArticleTitle());
        postDTO.setPostExcerpt(article.getArticleExcerpt());
        postDTO.setPostContent(article.getArticleContent());
        postDTO.setPostStatus(article.getArticleStatus().getStatusKey());
        return postDTO;
    }
}
