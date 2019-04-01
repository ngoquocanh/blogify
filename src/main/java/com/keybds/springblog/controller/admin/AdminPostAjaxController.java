package com.keybds.springblog.controller.admin;

import com.keybds.springblog.api.AjaxResponse;
import com.keybds.springblog.constants.UrlConstants;
import com.keybds.springblog.dto.PostDTO;
import com.keybds.springblog.enums.StatusMessageCode;
import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.model.Article;
import com.keybds.springblog.service.ArticleService;
import com.keybds.springblog.utils.PostUtil;
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
