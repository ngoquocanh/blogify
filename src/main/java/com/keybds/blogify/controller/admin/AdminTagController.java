package com.keybds.blogify.controller.admin;

import com.keybds.blogify.components.WebUI;
import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.containers.PageHolder;
import com.keybds.blogify.containers.PageUtil;
import com.keybds.blogify.controller.BaseController;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Tag;
import com.keybds.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminTagController extends BaseController {

    @Autowired
    private TagService tagService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_ADMIN_TAGS_LIST     = "admin/tags/list";

    /**
     * URL: /admin/tags?page=
     * METHOD: GET
     * @param strPageIndex
     * @return pager
     * @throws Exception
     */
    @GetMapping(UrlConstants.ADMIN_TAGS_LIST)
    public ModelAndView retrieveAllTagsPageable(@RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX) String strPageIndex) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_TAGS_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        if (pageIndex != 0) {
            Page<Tag> tags = tagService.getAllTags(PageRequest.of(pageIndex - 1, PAGE_SIZE));
            if (tags.hasContent()) {
                PageUtil<Tag> pager = manualBuildPager(tags.getContent(), tags.getNumber(), tags.getSize(), tags.getTotalElements());
                PageHolder<Tag> pagePostsHolder = new PageHolder(pager, UrlConstants.ADMIN_TAGS_LIST_BASE_URL);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
            } else {
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.ADMIN_TAGS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }
}
