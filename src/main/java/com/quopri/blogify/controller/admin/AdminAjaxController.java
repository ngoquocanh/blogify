package com.quopri.blogify.controller.admin;

import com.quopri.blogify.api.AjaxResponse;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.dto.PostDTO;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.model.Article;
import com.quopri.blogify.service.ArticleService;
import com.quopri.blogify.utils.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminAjaxController extends BaseController {

    @Autowired
    private ArticleService postService;

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
}
