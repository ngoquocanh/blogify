package com.quopri.blogify.controller.admin;

import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.service.ArticleService;
import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.containers.PageHolder;
import com.quopri.blogify.containers.PageUtil;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.dto.PostDTO;
import com.quopri.blogify.entity.Article;
import com.quopri.blogify.utils.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminPostController extends BaseController {

    @Autowired
    private ArticleService postService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_ADMIN_POSTS_LIST     = "admin/posts/list";
    protected static final String VIEW_ADMIN_POST_UPDATE    = "admin/posts/update";
    protected static final String VIEW_ADMIN_POST_ADD       = "admin/posts/add";

    public static final String MODEL_ATTRIBUTE_PAGE_INDEX            = "page";
    public static final String MODEL_ATTRIBUTE_PAGE_SORT_NAME        = "sort";
    public static final String MODEL_ATTRIBUTE_PAGE_SORT_DIR         = "dir";
    public static final String MODEL_ATTRIBUTE_PAGE_DEFAULT          = "0";
    public static final String MODEL_ATTRIBUTE_POST_ID               = "id";
    public static final String MODEL_ATTRIBUTE_POST                  = "post";
    public static final String MODEL_ATTRIBUTE_SORT_NAME_POST_TITLE  = "title";
    public static final String MODEL_ATTRIBUTE_SORT_NAME_POST_DATE   = "date";
    public static final String MODEL_ATTRIBUTE_SORT_DIR_DESC         = "desc";

    public static final String FEEDBACK_MESSAGE_KEY_POST_UPDATED  = "feedback.message.post.updated";
    public static final String FEEDBACK_MESSAGE_KEY_POST_ADDED    = "feedback.message.post.added";
    public static final String FEEDBACK_MESSAGE_KEY_POST_DELETED  = "feedback.message.post.deleted";
    public static final String FEEDBACK_MESSAGE_KEY_POSTS_DELETED = "feedback.message.posts.deleted";

    public static final String ENTITY_SORT_POST_TITLE = "articleTitle";
    public static final String ENTITY_SORT_POST_DATE  = "articleDate";

    /**
     * URL: /admin/posts?page=
     * METHOD: GET
     * @param strPageIndex
     * @return pager
     * @throws Exception
     */
    @GetMapping(value = {UrlConstants.ADMIN_PREFIX, UrlConstants.ADMIN_POSTS_LIST})
    public ModelAndView retrieveAllPostsPageableDefault(
            @RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX, defaultValue = MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_POSTS_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        if (pageIndex != 0) {
            Page<Article> posts = postService.getAllPosts(PageRequest.of(pageIndex - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, ENTITY_SORT_POST_DATE)));
            if (posts.hasContent()) {
                PageUtil<Article> pager = manualBuildPager(posts.getContent(), posts.getNumber(), posts.getSize(), posts.getTotalElements());
                PageHolder<Article> pagePostsHolder = new PageHolder(pager, UrlConstants.ADMIN_POSTS_LIST_BASE_URL);
                pagePostsHolder.setSortName(MODEL_ATTRIBUTE_SORT_NAME_POST_DATE);
                pagePostsHolder.setSortDir(MODEL_ATTRIBUTE_SORT_DIR_DESC);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
            } else {
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/posts
     * METHOD: POST
     * @param strPageIndex
     * @param strSortName
     * @param strSortDir
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_POSTS_LIST)
    public ModelAndView retrieveAllPostsPageableAndSortable(
            @RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX, defaultValue = MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex,
            @RequestParam(value = MODEL_ATTRIBUTE_PAGE_SORT_NAME, required = false, defaultValue = MODEL_ATTRIBUTE_SORT_NAME_POST_TITLE) String strSortName,
            @RequestParam(value = MODEL_ATTRIBUTE_PAGE_SORT_DIR, required = false, defaultValue = MODEL_ATTRIBUTE_SORT_DIR_DESC) String strSortDir
    ) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_POSTS_LIST);
        String sortDir = isSortDir(strSortDir) ? strSortDir : MODEL_ATTRIBUTE_SORT_DIR_DESC;
        Integer pageIndex = parseInt(strPageIndex, 0);
        String sortName = ENTITY_SORT_POST_TITLE;
        if (strSortName.equalsIgnoreCase(MODEL_ATTRIBUTE_SORT_NAME_POST_DATE)) {
            sortName = ENTITY_SORT_POST_DATE;
        } else if (strSortName.equalsIgnoreCase(MODEL_ATTRIBUTE_SORT_NAME_POST_TITLE)) {
            sortName = ENTITY_SORT_POST_TITLE;
        }
        if (pageIndex != 0) {
            Page<Article> posts = postService.getAllPosts(pageIndex - 1, PAGE_SIZE, sortName, sortDir);
            if (posts.hasContent()) {
                PageUtil<Article> pager = manualBuildPager(posts.getContent(), posts.getNumber(), posts.getSize(), posts.getTotalElements());
                PageHolder<Article> pagePostsHolder = new PageHolder(pager, UrlConstants.ADMIN_POSTS_LIST_BASE_URL);
                if (sortName == ENTITY_SORT_POST_TITLE) {
                    pagePostsHolder.setSortName(MODEL_ATTRIBUTE_SORT_NAME_POST_TITLE);
                } else if (sortName == ENTITY_SORT_POST_DATE) {
                    pagePostsHolder.setSortName(MODEL_ATTRIBUTE_SORT_NAME_POST_DATE);
                }
                pagePostsHolder.setSortDir(sortDir);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
            } else {
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/posts/add
     * METHOD: GET
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_POST_ADD)
    public ModelAndView openFormAddPost() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_POST_ADD);
        PostDTO postDTO = new PostDTO();
        mav.addObject(MODEL_ATTRIBUTE_POST, postDTO);
        return mav;
    }

    /**
     * URL: /admin/posts/add
     * METHOD: POST
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_POST_ADD)
    public ModelAndView createPost(@Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) PostDTO postDTO,
                                   BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_POST, postDTO);
            mav.setViewName(VIEW_ADMIN_POST_ADD);
        } else {
            // create slug base on post title
            String slug = postService.createSlug(postDTO.getPostTitle());
            postDTO.setPostName(slug);
            Article article = PostUtil.convertToEntity(postDTO, true);
            Article articleCreated = postService.createPost(article);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_POST_ADDED, articleCreated.getArticleTitle());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/posts/update/{id}
     * METHOD: GET
     * @param strPostId
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_POST_UPDATE_ID)
    public ModelAndView openFormUpdatePost(@PathVariable(MODEL_ATTRIBUTE_POST_ID) String strPostId) throws MvcException {
        Long postId = parseLong(strPostId, 0L);
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_POST_UPDATE);
        if (postId != 0L) {
            Article articleFound = postService.getPost(postId);
            PostDTO postToUpdate = PostUtil.convertToDTO(articleFound);
            mav.addObject(MODEL_ATTRIBUTE_POST, postToUpdate);
        } else {
            mav.addObject(MODEL_ATTRIBUTE_POST, null);
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return mav;
    }

    /**
     * URL: /admin/posts/update
     * METHOD: POST
     * @param postDTO
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_POST_UPDATE)
    public ModelAndView updatePost(@Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) PostDTO postDTO,
                                   BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_POST, postDTO);
            mav.setViewName(VIEW_ADMIN_POST_UPDATE);
        } else {
            // need to check if article name is duplicated
            Article article = PostUtil.convertToEntity(postDTO, false);
            Article articleUpdated = postService.updatePost(article);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_POST_UPDATED, articleUpdated.getId());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/posts/delete/{id}
     * METHOD: GET
     * @param strPostId
     * @param attributes
     * @return
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ADMIN_POST_DELETE_ID)
    public ModelAndView deletePost(@PathVariable(MODEL_ATTRIBUTE_POST_ID) String strPostId, RedirectAttributes attributes) throws MvcException {
        Long postId = parseLong(strPostId, 0L);
        ModelAndView mav = new ModelAndView();
        if (postId != 0L) {
            postService.deletePost(postId);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_POST_DELETED);
            mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        } else {
            throw new MvcException(StatusMessageCode.POST_NOT_FOUND);
        }
        return mav;
    }

    /**
     * URL: /admin/posts/delete
     * METHOD: POST
     * @param strPostIds
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_POSTS_DELETE)
    public ModelAndView deletePosts(@RequestParam(MODEL_ATTRIBUTE_POST_ID) String[] strPostIds, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        List<Long> postIds = parseLongIds(strPostIds);
        postService.deletePosts(postIds);
        webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_POSTS_DELETED);
        mav.setViewName(redirectTo(UrlConstants.ADMIN_POSTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        return mav;
    }
}
