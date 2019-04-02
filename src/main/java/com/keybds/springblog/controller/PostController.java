package com.keybds.springblog.controller;

import com.keybds.springblog.containers.PageHolder;
import com.keybds.springblog.containers.PageUtil;
import com.keybds.springblog.controller.admin.AdminPostController;
import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.service.ArticleService;
import com.keybds.springblog.constants.UrlConstants;
import com.keybds.springblog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController extends BaseController {

    @Autowired
    private ArticleService postService;

    protected static final String VIEW_PUBLIC_POSTS_LIST    = "public/posts/list";
    protected static final String VIEW_PUBLIC_SINGLE_POST   = "public/posts/post";

    public static final String MODEL_ATTRIBUTE_PAGE_INDEX   = "page";
    public static final String MODEL_ATTRIBUTE_ARTICLE_LINK = "articleLink";
    public static final String MODEL_ATTRIBUTE_ARTICLE      = "article";

    /**
     * default public posts page
     * URL: /articles
     * @return pager
     */
    @GetMapping(value = {UrlConstants.HOME, UrlConstants.PUBLIC_ARTICLES_LIST})
    public ModelAndView retrieveAllPublishedPosts() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_PUBLIC_POSTS_LIST);
        Page<Article> posts = postService.getAllPublishedPosts(PageRequest.of(0, PAGE_SIZE));
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
        if (pageIndex != 0) {
            Page<Article> posts = postService.getAllPublishedPosts(PageRequest.of(pageIndex - 1, PAGE_SIZE));
            return buildPublicPosts(mav, posts);
        } else {
            mav.setViewName(redirectTo(UrlConstants.PUBLIC_ARTICLES_LIST));
            return mav;
        }
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
