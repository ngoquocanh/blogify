package com.quopri.blogify.controller.stranger;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.containers.PageHolder;
import com.quopri.blogify.containers.PageUtil;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.controller.admin.AdminPostController;
import com.quopri.blogify.entity.Tag;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.service.ArticleService;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.entity.Article;
import com.quopri.blogify.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class PostController extends BaseController {

    @Autowired
    private ArticleService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private WebUI webUI;

    protected static final String VIEW_PUBLIC_POSTS_LIST     = "public/posts/list";
    protected static final String VIEW_PUBLIC_SINGLE_POST    = "public/posts/post";
    protected static final String VIEW_PUBLIC_POSTS_TAG_LIST = "public/tags/list";

    public static final String MODEL_ATTRIBUTE_PAGE_INDEX   = "page";
    public static final String MODEL_ATTRIBUTE_ARTICLE_LINK = "articleLink";
    public static final String MODEL_ATTRIBUTE_ARTICLE      = "article";
    public static final String MODEL_ATTRIBUTE_TAG_VALUE    = "tag";

    /**
     * default public posts page
     * URL: /articles
     * @return pager
     */
    @GetMapping(value = {UrlConstants.HOME, UrlConstants.PUBLIC_ARTICLES_LIST})
    public ModelAndView retrieveAllPublishedPosts() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_PUBLIC_POSTS_LIST);
        Page<Article> posts = postService.getAllPublishedPosts(PageRequest.of(0, PAGE_SIZE));
        webUI.addPageTitle(mav);
        return buildPublicPosts(mav, posts);
    }

    /**
     * URL: /articles/p/{page}
     * @param strPageIndex
     * @return pager
     */
    @GetMapping(UrlConstants.PUBLIC_POSTS_LIST_PAGE)
    public ModelAndView retrieveAllPublishedPostsPageable(@PathVariable(MODEL_ATTRIBUTE_PAGE_INDEX) String strPageIndex) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_PUBLIC_POSTS_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        webUI.addPageTitle(mav);
        if (pageIndex != 0) {
            Page<Article> posts = postService.getAllPublishedPosts(PageRequest.of(pageIndex - 1, PAGE_SIZE));
            return buildPublicPosts(mav, posts);
        } else {
            mav.setViewName(redirectTo(UrlConstants.PUBLIC_ARTICLES_LIST));
            return mav;
        }
    }

    /**
     * URL: /articles/tag/{tag}?page=
     * @param tagValue
     * @return pager
     */
    @GetMapping(UrlConstants.PUBLIC_ARTICLES_TAG)
    public ModelAndView retrieveAllPublishedPostsByTag(
            @PathVariable(MODEL_ATTRIBUTE_TAG_VALUE) String tagValue,
            @RequestParam(value = MODEL_ATTRIBUTE_PAGE_INDEX, defaultValue = MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex) throws MvcException, UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView(VIEW_PUBLIC_POSTS_TAG_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        Tag tag = tagService.getTag(URLDecoder.decode(tagValue, "UTF-8"));
        if (pageIndex != 0) {
            Page<Article> posts = postService.getAllPublishedPostsByTag(PageRequest.of(pageIndex - 1, PAGE_SIZE), tag.getId());
            if (posts.hasContent()) {
                PageUtil<Article> pager = manualBuildPager(posts.getContent(), posts.getNumber(), posts.getSize(), posts.getTotalElements());
                PageHolder<Article> pagePostsHolder = new PageHolder(pager, UrlConstants.PUBLIC_ARTICLES_TAG_BASE_URL);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
                webUI.addPageTitle(mav, tag.getValue());
            } else {
                webUI.addPageTitle(mav);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.PUBLIC_ARTICLES_TAG_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /article/{articleLink}
     * @param articleLink
     * @return
     * @throws Exception
     */
    @GetMapping(UrlConstants.PUBLIC_ARTICLE_LINK)
    public ModelAndView retrievePost(@PathVariable(MODEL_ATTRIBUTE_ARTICLE_LINK) String articleLink) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_PUBLIC_SINGLE_POST);
        Article article = postService.getPost(articleLink);
        webUI.addPageTitle(mav, article.getArticleTitle());
        mav.addObject(MODEL_ATTRIBUTE_ARTICLE, article);
        return mav;
    }

    /**
     * prevent duplicate code
     * @param mav
     * @param posts
     * @return
     */
    private ModelAndView buildPublicPosts(ModelAndView mav, Page<Article> posts) {
        ModelAndView mavResult = mav;
        if (posts.hasContent()) {
            PageUtil<Article> pager = manualBuildPager(posts.getContent(), posts.getNumber(), posts.getSize(), posts.getTotalElements());
            PageHolder<Article> pagePostsHolder = new PageHolder(pager, UrlConstants.PUBLIC_POSTS_LIST_BASE_URL);
            mavResult.addObject(AdminPostController.MODEL_ATTRIBUTE_PAGER, pagePostsHolder);
        } else {
            mavResult.addObject(AdminPostController.MODEL_ATTRIBUTE_PAGER, null);
        }
        return mavResult;
    }
}
