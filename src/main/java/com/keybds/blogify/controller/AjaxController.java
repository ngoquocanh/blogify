package com.keybds.blogify.controller;

import com.keybds.blogify.configuration.ApplicationSettings;
import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Tag;
import com.keybds.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class AjaxController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ApplicationSettings applicationSettings;

    @GetMapping(value = UrlConstants.PUBLIC_AJAX_POST_TAG_CLOUD)
    public String retrieveAllTagCloud() throws MvcException {
        List<Tag> tags = tagService.getAllTags();
        StringBuilder tagsHtml = new StringBuilder();
        tagsHtml.append("<ul class='tag-list'>");
        for (Tag tag : tags) {
            tagsHtml.append(buildTagHtml(tag));
        }
        tagsHtml.append("</ul>");
        return tagsHtml.toString();
    }

    private String buildTagHtml(Tag tag) {
        String tagPattern = "<li><a href='%s/articles/tag/%s' class='%s'>%s</a></li>";
        return String.format(tagPattern, applicationSettings.getBaseUrl(), tag.getValue().toLowerCase(), getCssClassTag(), tag.getValue());
    }

    /**
     * available css class:
     * minimum-tag (1)
     * small-tag (2)
     * medium-tag (3)
     * large-tag (4)
     * maximum-tag (5)
     * @return
     */
    private String getCssClassTag() {
        int max = 5;
        int min = 1;
        Random random = new Random();
        int numSize = random.nextInt((max - min) + 1) + min;
        String cssClass = "medium-tag";
        switch (numSize) {
            case 1:
                cssClass = "minimum-tag";
                break;
            case 2:
                cssClass = "small-tag";
                break;
            case 4:
                cssClass = "large-tag";
                break;
            case 5:
                cssClass = "maximum-tag";
                break;
            case 3:
            default:
                break;
        }
        return cssClass;
    }
}
