package com.keybds.blogify.controller.admin;

import com.keybds.blogify.components.WebUI;
import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.containers.PageHolder;
import com.keybds.blogify.containers.PageUtil;
import com.keybds.blogify.controller.BaseController;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Category;
import com.keybds.blogify.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminCategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_ADMIN_CATEGORIES_LIST              = "admin/categories/list";
    protected static final String MODEL_ATTRIBUTE_CATEGORY_ID             = "id";
    protected static final String FEEDBACK_MESSAGE_KEY_CATEGORIES_DELETED = "feedback.message.categories.deleted";

    /**
     * URL: /admin/categories?page=
     * METHOD: GET
     * @param strPageIndex
     * @return pager
     * @throws Exception
     */
    @GetMapping(UrlConstants.ADMIN_CATEGORIES_LIST)
    public ModelAndView retrieveAllCategoriesPageable(@RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX, defaultValue = MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_CATEGORIES_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        if (pageIndex != 0) {
            Page<Category> categories = categoryService.getAllCategories(PageRequest.of(pageIndex - 1, PAGE_SIZE));
            if (categories.hasContent()) {
                PageUtil<Category> pager = manualBuildPager(categories.getContent(), categories.getNumber(), categories.getSize(), categories.getTotalElements());
                PageHolder<Category> pagePostsHolder = new PageHolder(pager, UrlConstants.ADMIN_CATEGORIES_LIST_BASE_URL);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
            } else {
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.ADMIN_CATEGORIES_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/categories/delete
     * METHOD: POST
     * @param strCategoryIds
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_CATEGORIES_DELETE)
    public ModelAndView deleteCategories(@RequestParam(MODEL_ATTRIBUTE_CATEGORY_ID) String[] strCategoryIds, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        List<Long> categoryIds = parseLongIds(strCategoryIds);
        categoryService.deleteCategories(categoryIds);
        webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORIES_DELETED);
        mav.setViewName(redirectTo(UrlConstants.ADMIN_CATEGORIES_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        return mav;
    }
}
