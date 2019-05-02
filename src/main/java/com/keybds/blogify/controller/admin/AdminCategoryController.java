package com.keybds.blogify.controller.admin;

import com.keybds.blogify.components.WebUI;
import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.containers.PageHolder;
import com.keybds.blogify.containers.PageUtil;
import com.keybds.blogify.controller.BaseController;
import com.keybds.blogify.dto.CategoryDTO;
import com.keybds.blogify.enums.StatusMessageCode;
import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Category;
import com.keybds.blogify.service.CategoryService;
import com.keybds.blogify.utils.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminCategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_ADMIN_CATEGORIES_LIST              = "admin/categories/list";
    protected static final String VIEW_ADMIN_CATEGORY_UPDATE              = "admin/categories/update";
    protected static final String VIEW_ADMIN_CATEGORY_ADD                 = "admin/categories/add";

    protected static final String MODEL_ATTRIBUTE_CATEGORY_ID             = "id";
    protected static final String MODEL_ATTRIBUTE_CATEGORY                = "category";

    protected static final String FEEDBACK_MESSAGE_KEY_CATEGORIES_DELETED = "feedback.message.categories.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_CATEGORY_UPDATED  = "feedback.message.category.updated";
    protected static final String FEEDBACK_MESSAGE_KEY_CATEGORY_ADDED    = "feedback.message.category.added";

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
     * URL: /admin/categories/update/{id}
     * METHOD: GET
     * @param strCategoryId
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_CATEGORY_UPDATE_ID)
    public ModelAndView openFormUpdateCategory(@PathVariable(MODEL_ATTRIBUTE_CATEGORY_ID) String strCategoryId) throws MvcException {
        Long categoryId = parseLong(strCategoryId, 0L);
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_CATEGORY_UPDATE);
        if (categoryId != 0L) {
            Category categoryFound = categoryService.getCategory(categoryId);
            CategoryDTO categoryToUpdate = CategoryUtil.convertToDTO(categoryFound);
            mav.addObject(MODEL_ATTRIBUTE_CATEGORY, categoryToUpdate);
        } else {
            mav.addObject(MODEL_ATTRIBUTE_CATEGORY, null);
            throw new MvcException(StatusMessageCode.CATEGORY_NOT_FOUND);
        }
        return mav;
    }

    /**
     * URL: /admin/categories/update
     * METHOD: POST
     * @param categoryDTO
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_CATEGORY_UPDATE)
    public ModelAndView updateCategory(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CATEGORY) CategoryDTO categoryDTO,
                                   BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_CATEGORY, categoryDTO);
            mav.setViewName(VIEW_ADMIN_CATEGORY_UPDATE);
        } else {
            Category category = CategoryUtil.convertToEntity(categoryDTO, false);
            Category categoryUpdated = categoryService.updateCategory(category);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORY_UPDATED, categoryUpdated.getId());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_CATEGORIES_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/categories/add
     * METHOD: GET
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_CATEGORY_ADD)
    public ModelAndView openFormAddCategory() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_CATEGORY_ADD);
        CategoryDTO categoryDTO = new CategoryDTO();
        mav.addObject(MODEL_ATTRIBUTE_CATEGORY, categoryDTO);
        return mav;
    }

    /**
     * URL: /admin/categories/add
     * METHOD: POST
     * @param categoryDTO
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_CATEGORY_ADD)
    public ModelAndView createCategory(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CATEGORY) CategoryDTO categoryDTO,
                                  BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_CATEGORY, categoryDTO);
            mav.setViewName(VIEW_ADMIN_CATEGORY_ADD);
        } else {
            Category category = CategoryUtil.convertToEntity(categoryDTO, true);
            Category categoryCreated = categoryService.createCategory(category);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORY_ADDED, categoryCreated.getValue());
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
