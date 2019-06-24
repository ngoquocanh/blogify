package com.quopri.blogify.dto;

import com.quopri.blogify.entity.Article;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class PostDTO implements Serializable {

    private Long postId;

    @NotEmpty(message = "Please enter value for title filed")
    @Length(max = 1500, message = "please enter value for title field less than {max} characters")
    private String postTitle;

    @NotEmpty(message = "Please enter value for excerpt filed")
    private String postExcerpt;

    @NotEmpty(message = "Please enter value for content filed")
    @Length(min = 20, message = "Please enter value for content field greater than {min} characters")
    private String postContent;

    private String postThumbnail;

    private Integer postStatus;

    private List<PostStatusOption> postStatusOptions = new ArrayList<>(Arrays.asList(
        new PostStatusOption(Article.Status.DRAFT.getKey(), Article.Status.DRAFT.getValue()),
        new PostStatusOption(Article.Status.PUBLISHED.getKey(), Article.Status.PUBLISHED.getValue())
    ));

    private String postName;

    @NotEmpty(message = "Please enter at least one tag")
    private String postTags;

    @Data
    private class PostStatusOption {
        private Integer statusKey;
        private String statusValue;

        public PostStatusOption(Integer statusKey, String statusValue) {
            this.setStatusKey(statusKey);
            this.setStatusValue(statusValue);
        }
    }
}
