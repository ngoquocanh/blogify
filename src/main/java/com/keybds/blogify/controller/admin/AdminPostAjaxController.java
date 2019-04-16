package com.keybds.blogify.controller.admin;

import com.keybds.blogify.api.AjaxResponse;
import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.dto.PostDTO;
import com.keybds.blogify.enums.StatusMessageCode;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Article;
import com.keybds.blogify.service.ArticleService;
import com.keybds.blogify.utils.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminPostAjaxController {

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
