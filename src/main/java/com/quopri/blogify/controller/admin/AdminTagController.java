package com.quopri.blogify.controller.admin;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.containers.PageHolder;
import com.quopri.blogify.containers.PageUtil;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.dto.TagDTO;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.entity.Tag;
import com.quopri.blogify.service.TagService;
import com.quopri.blogify.utils.TagUtil;
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
public class AdminTagController extends BaseController {

    @Autowired
    private TagService tagService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_ADMIN_TAGS_LIST     = "admin/tags/list";
    protected static final String VIEW_ADMIN_TAG_UPDATE    = "admin/tags/update";
    protected static final String VIEW_ADMIN_TAG_ADD       = "admin/tags/add";

    public static final String MODEL_ATTRIBUTE_TAG_ID      = "id";
    protected static final String MODEL_ATTRIBUTE_TAG      = "tag";

    protected static final String FEEDBACK_MESSAGE_KEY_TAGS_DELETED = "feedback.message.tags.deleted";
    protected static final String FEEDBACK_MESSAGE_KEY_TAG_UPDATED  = "feedback.message.tag.updated";
    protected static final String FEEDBACK_MESSAGE_KEY_TAG_ADDED    = "feedback.message.tag.added";

    /**
     * URL: /admin/tags?page=
     * METHOD: GET
     * @param strPageIndex
     * @return pager
     * @throws Exception
     */
    @GetMapping(UrlConstants.ADMIN_TAGS_LIST)
    public ModelAndView retrieveAllTagsPageable(@RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX, defaultValue = MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex) throws MvcException {
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

    /**
     * URL: /admin/tags/update/{id}
     * METHOD: GET
     * @param strTagId
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_TAG_UPDATE_ID)
    public ModelAndView openFormUpdateTag(@PathVariable(MODEL_ATTRIBUTE_TAG_ID) String strTagId) throws MvcException {
        Long tagId = parseLong(strTagId, 0L);
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_TAG_UPDATE);
        if (tagId != 0L) {
            Tag tagFound = tagService.getTag(tagId);
            TagDTO tagToUpdate = TagUtil.convertToDTO(tagFound);
            mav.addObject(MODEL_ATTRIBUTE_TAG, tagToUpdate);
        } else {
            mav.addObject(MODEL_ATTRIBUTE_TAG, null);
            throw new MvcException(StatusMessageCode.TAG_NOT_FOUND);
        }
        return mav;
    }

    /**
     * URL: /admin/tags/update
     * METHOD: POST
     * @param tagDTO
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_TAG_UPDATE)
    public ModelAndView updateTag(@Valid @ModelAttribute(MODEL_ATTRIBUTE_TAG) TagDTO tagDTO,
                                   BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_TAG, tagDTO);
            mav.setViewName(VIEW_ADMIN_TAG_UPDATE);
        } else {
            Tag tag = TagUtil.convertToEntity(tagDTO, false);
            Tag tagUpdated = tagService.updateTag(tag);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TAG_UPDATED, tagUpdated.getId());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_TAGS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/tags/add
     * METHOD: GET
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_TAG_ADD)
    public ModelAndView openFormAddPost() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_TAG_ADD);
        TagDTO tagDTO = new TagDTO();
        mav.addObject(MODEL_ATTRIBUTE_TAG, tagDTO);
        return mav;
    }

    /**
     * URL: /admin/tags/add
     * METHOD: POST
     * @param tagDTO
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_TAG_ADD)
    public ModelAndView createTag(@Valid @ModelAttribute(MODEL_ATTRIBUTE_TAG) TagDTO tagDTO,
                                   BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_TAG, tagDTO);
            mav.setViewName(VIEW_ADMIN_TAG_ADD);
        } else {
            Tag tag = TagUtil.convertToEntity(tagDTO, true);
            Tag tagCreated = tagService.createTag(tag);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TAG_ADDED, tagCreated.getValue());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_TAGS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/tags/delete
     * METHOD: POST
     * @param strTagIds
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_TAGS_DELETE)
    public ModelAndView deleteTags(@RequestParam(MODEL_ATTRIBUTE_TAG_ID) String[] strTagIds, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        List<Long> tagIds = parseLongIds(strTagIds);
        tagService.deleteTags(tagIds);
        webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_TAGS_DELETED);
        mav.setViewName(redirectTo(UrlConstants.ADMIN_TAGS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        return mav;
    }

}
