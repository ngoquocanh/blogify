package com.quopri.blogify.dto;

import com.quopri.blogify.entity.Article;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDTO implements Serializable {

    private Long postId;
    private Integer postStatus;
    private List<PostStatusOption> postStatusOptions = new ArrayList<>(Arrays.asList(
        new PostStatusOption(Article.Status.DRAFT.getKey(), Article.Status.DRAFT.getValue()),
        new PostStatusOption(Article.Status.PUBLISHED.getKey(), Article.Status.PUBLISHED.getValue())
    ));

    @NotEmpty(message = "Please enter value for title filed")
    @Length(max = 1500, message = "please enter value for title field less than {max} characters")
    private String postTitle;

    private String postThumbnail;

    @NotEmpty(message = "Please enter value for excerpt filed")
    private String postExcerpt;

    @NotEmpty(message = "Please enter value for content filed")
    @Length(min = 20, message = "Please enter value for content field greater than {min} characters")
    private String postContent;

    private String postName;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public List<PostStatusOption> getPostStatusOptions() {
        return postStatusOptions;
    }

    public String getPostThumbnail() {
        return postThumbnail;
    }

    public void setPostThumbnail(String postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

    private class PostStatusOption {
        private Integer statusKey;
        private String statusValue;

        public PostStatusOption(Integer statusKey, String statusValue) {
            this.setStatusKey(statusKey);
            this.setStatusValue(statusValue);
        }

        public Integer getStatusKey() {
            return statusKey;
        }

        public void setStatusKey(Integer statusKey) {
            this.statusKey = statusKey;
        }

        public String getStatusValue() {
            return statusValue;
        }

        public void setStatusValue(String statusValue) {
            this.statusValue = statusValue;
        }
    }
}
