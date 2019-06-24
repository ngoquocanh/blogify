package com.quopri.blogify.controller.admin;

import com.quopri.blogify.api.AjaxResponse;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.dto.PostDTO;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.entity.Article;
import com.quopri.blogify.service.ArticleService;
import com.quopri.blogify.service.TagService;
import com.quopri.blogify.utils.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminAjaxController extends BaseController {

    @Autowired
    private ArticleService postService;

    @Autowired
    private TagService tagService;

    @PostMapping(UrlConstants.ADMIN_AJAX_POST_UPDATE)
    public AjaxResponse ajaxUpdatePost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult) throws MvcException {
        if (bindingResult.hasErrors()) {
            return new AjaxResponse(StatusMessageCode.FAIL);
        } else {
            Article article = PostUtil.convertToEntity(postDTO, false);
            postService.updatePost(article);
            return new AjaxResponse(StatusMessageCode.SUCCESS);
        }
    }

    @GetMapping(UrlConstants.ADMIN_AJAX_TAGS_VALUES)
    public List<String> ajaxRetrieveTagValues() {
        return tagService.getAllTagValues();
    }

}
